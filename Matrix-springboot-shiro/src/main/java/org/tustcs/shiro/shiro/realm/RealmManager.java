package org.tustcs.shiro.shiro.realm;

import org.apache.shiro.realm.Realm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.tustcs.shiro.pojo.token.JwtToken;
import org.tustcs.shiro.service.ISAccountService;
import org.tustcs.shiro.shiro.matcher.JwtMatcher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 初始化realm
 * @author Lin
 */
@Component
public class RealmManager {
    private JwtMatcher jwtMatcher;

    @Autowired
    public RealmManager(JwtMatcher jwtMatcher) {
        this.jwtMatcher = jwtMatcher;
    }

    public List<Realm> initRealm() {
        List<Realm> realmList = new LinkedList<>();

        JwtRealm jwtRealm = new JwtRealm();
        jwtRealm.setCredentialsMatcher(jwtMatcher);
        jwtRealm.setAuthenticationTokenClass(JwtToken.class);
        realmList.add(jwtRealm);

        return Collections.unmodifiableList(realmList);
    }
}
