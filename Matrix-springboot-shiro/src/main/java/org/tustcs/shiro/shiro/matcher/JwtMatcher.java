package org.tustcs.shiro.shiro.matcher;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.stereotype.Component;
import org.tustcs.shiro.pojo.JwtAccount;
import org.tustcs.shiro.util.JWTUtil;

/**
 * jwt解密Matcher
 * @author Lin
 */
@Component
public class JwtMatcher implements CredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {

        String jwt = (String) authenticationInfo.getCredentials();
        JwtAccount jwtAccount = null;
        try {
            jwtAccount = JWTUtil.parseJwt(jwt, JWTUtil.SECRET_KEY);
        } catch (SignatureException | UnsupportedJwtException | MalformedJwtException | IllegalArgumentException e) {
            throw new AuthenticationException("errJwt"); // 令牌错误
        } catch (ExpiredJwtException e) {

            throw new AuthenticationException("expiredJwt"); // 令牌过期
        } catch (Exception e) {
            throw new AuthenticationException("errJwt");
        }
        if (null == jwtAccount) {
            throw new AuthenticationException("errJwt");
        }

        return true;
    }
}
