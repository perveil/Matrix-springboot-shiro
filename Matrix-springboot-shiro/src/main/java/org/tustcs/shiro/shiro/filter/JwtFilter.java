package org.tustcs.shiro.shiro.filter;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tustcs.shiro.config.Config;
import org.tustcs.shiro.pojo.ResponseBean;
import org.tustcs.shiro.pojo.enums.StatusEnum;
import org.tustcs.shiro.pojo.token.JwtToken;
import org.tustcs.shiro.service.ISAccountService;
import org.tustcs.shiro.util.JWTUtil;
import org.tustcs.shiro.util.RequestResponseUtil;

import javax.naming.AuthenticationException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * 基于Ant风格的路径匹配jwt过滤器
 * @author Lin
 */
@Slf4j
public class JwtFilter extends PathMatchingFilter {

    private static final String AUTHORIZATION = "Authorization";

    private static final String UID           = "Uid";

    private static final String ISSUER        = "server";

    private static final String EXP_MSG       = "expiredJwt";

    private StringRedisTemplate redisTemplate;

    private ISAccountService    accountService;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setAccountService(ISAccountService accountService) {
        this.accountService = accountService;
    }

    private Subject getSubject(ServletRequest request, ServletResponse response) {
        return SecurityUtils.getSubject();
    }

    private boolean isJwtSub(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(AUTHORIZATION);
        String uid = req.getHeader(UID);
        return !StringUtils.isEmpty(authorization) && !StringUtils.isEmpty(uid);
    }

    private boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader(AUTHORIZATION);
        String uid = httpServletRequest.getHeader(UID);
        JwtToken token = new JwtToken(uid, authorization);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(token);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    private boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        Subject subject = getSubject(request, response);
        if (isJwtSub(request, response)) {
            try {
                executeLogin(request, response);
                HttpServletRequest servletRequest = (HttpServletRequest) request;
                if (checkIsRefreshed(servletRequest.getHeader(UID), servletRequest.getHeader(AUTHORIZATION))) {
                    RequestResponseUtil.responseWrite(JSON.toJSONString(new ResponseBean(StatusEnum.REFRESHED_JWT)), response);
                    return false;
                }
                return this.checkRoles(subject, mappedValue);
            } catch (AuthenticationException e) {
                // 若JWT为已过期，签发新的令牌
                if (EXP_MSG.equals(e.getMessage())) {
                    String uid = RequestResponseUtil.getHeader(request, UID);
                    String roles = accountService.getRoleByUser(uid);
                    String refreshJwt = JWTUtil.issueJWT(UUID.randomUUID().toString(), uid, ISSUER, Config.REFRESH_TIME, roles, null, SignatureAlgorithm.HS512);
                    redisTemplate.opsForValue().set(Config.REDIS_JWT_PREFIX + uid, refreshJwt, Config.REFRESH_TIME);
                    RequestResponseUtil.responseWrite(JSON.toJSONString(new ResponseBean(StatusEnum.NEW_JWT)), response);
                    return false;
                }
            } catch (Exception e) {
                log.error(e.getMessage());
                RequestResponseUtil.responseWrite(JSON.toJSONString(new ResponseBean(StatusEnum.ERR_JWT)), response);
                return false;
            }
        } else {
            RequestResponseUtil.responseWrite(JSON.toJSONString(new ResponseBean(StatusEnum.NO_JWT)), response);
            return false;
        }
        return true;
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    private boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        // 认证失败，抛出错误JWT
        if (!subject.isAuthenticated()) {
            ResponseBean res = new ResponseBean(StatusEnum.ERR_JWT);
            RequestResponseUtil.responseWrite(JSON.toJSONString(res), response);
        } else {
            // 已经认证但未授权的情况
            // 告知客户端JWT没有权限访问此资源
            ResponseBean res = new ResponseBean(StatusEnum.NO_PERMISSION);
            RequestResponseUtil.responseWrite(JSON.toJSONString(res), response);
        }
        return false;
    }

    @Override
    protected boolean pathsMatch(String path, ServletRequest request) {
        String requestURI = this.getPathWithinApplication(request);
        log.info(path + " " + requestURI + " " + this.pathsMatch(path, requestURI));
        return this.pathsMatch(path, requestURI);

    }

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return isAccessAllowed(request, response, mappedValue) || onAccessDenied(request, response);
    }

    /**
     * 验证当前用户是否属于mappedValue任意一个角色
     * @param subject
     * @param mappedValue
     * @return
     */
    private boolean checkRoles(Subject subject, Object mappedValue) {
        String[] rolesArray = (String[]) mappedValue;
        return rolesArray == null || rolesArray.length == 0 || Stream.of(rolesArray).anyMatch(role -> subject.hasRole(role.trim()));
    }

    private boolean checkIsRefreshed(String uid, String jwt) {
        String redisJwt = redisTemplate.opsForValue().get(Config.REDIS_JWT_PREFIX + uid);
        return !jwt.equals(redisJwt);
    }
}
