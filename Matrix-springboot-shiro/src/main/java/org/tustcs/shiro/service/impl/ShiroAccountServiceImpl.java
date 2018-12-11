package org.tustcs.shiro.service.impl;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.tustcs.shiro.config.Config;
import org.tustcs.shiro.dao.AuthResourceDAO;
import org.tustcs.shiro.dao.AuthUserDAO;
import org.tustcs.shiro.dao.AuthUserRoleDAO;
import org.tustcs.shiro.entity.AuthUser;
import org.tustcs.shiro.entity.AuthUserExample;
import org.tustcs.shiro.entity.AuthUserRole;
import org.tustcs.shiro.exception.SUserException;
import org.tustcs.shiro.pojo.JwtRes;
import org.tustcs.shiro.pojo.enums.StatusEnum;
import org.tustcs.shiro.service.ISAccountService;
import org.tustcs.shiro.util.JWTUtil;
import org.tustcs.shiro.util.MD5Utils;
import org.tustcs.shiro.util.RandomUtil;
import org.tustcs.shiro.util.UUIDUtils;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Lin
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ShiroAccountServiceImpl implements ISAccountService {

    @Autowired
    private AuthUserDAO         authUserDAO;

    @Autowired
    AuthUserRoleDAO             authUserRoleDAO;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AuthResourceDAO     authResourceDAO;

    @Override
    public String getRoleByUser(String uid) {
        return authUserDAO.selectRoles(uid);
    }

    @Override
    public JwtRes login(String username, String password) {
        AuthUserExample example = new AuthUserExample();
        example.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<AuthUser> authUsers = authUserDAO.selectByExample(example);
        if (authUsers.size() > 0) {
            AuthUser authUser = authUsers.get(0);
            if (authUser.getStatus().equals(0)) {
                throw new SUserException(StatusEnum.USER_FREEZE);
            }
            String uid = authUser.getUid();
            String roles = authUserDAO.selectRoles(uid);
            String perms = authResourceDAO.selectResources(uid, (short) 2);
            String key = Config.REDIS_JWT_PREFIX + uid;
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.isEmpty(value)) {
                redisTemplate.delete(key);
            }
            String jwt = JWTUtil.issueJWT(UUID.randomUUID().toString(), authUser.getUid(), Config.ISSUER, Config.REFRESH_TIME, roles, perms, SignatureAlgorithm.HS512);
            log.info(jwt);
            redisTemplate.opsForValue().set(key, jwt, Config.REFRESH_TIME, TimeUnit.SECONDS);
            String menus = authResourceDAO.selectAvailMenus(uid);
            return new JwtRes(jwt, uid, menus);
        } else {
            throw new SUserException(StatusEnum.LOGIN_ERROR);
        }
    }

    @Override
    public boolean isUserExist(String username, String phone) {
        AuthUserExample example = new AuthUserExample();
        example.createCriteria().andUsernameEqualTo(username).andPhoneEqualTo(phone);
        return authUserDAO.selectByExample(example).size() > 0;
    }

    @Override
    public boolean logout(String uid) {
        if (authUserDAO.selectByPrimaryKey(uid) == null) {
            throw new SUserException(StatusEnum.USER_NULL);
        }
        return redisTemplate.delete(Config.REDIS_JWT_PREFIX + uid);
    }

    @Override
    public boolean register(AuthUser authUser) {
        String uid = UUIDUtils.getUUID();
        String salt = RandomUtil.getRandomString(6);
        String password = MD5Utils.getMD5(authUser.getPassword() + salt);
        authUser.setUid(uid);
        authUser.setSalt(salt);
        authUser.setPassword(password);
        if (authUserDAO.insertSelective(authUser) > 0) {
            // 插入用户角色表，将该用户添加游客角色
            AuthUserRole userRole = new AuthUserRole();
            userRole.setRoleId(3);
            userRole.setUserId(uid);
            return authUserRoleDAO.insertSelective(userRole) > 0;
        }
        return false;
    }
}
