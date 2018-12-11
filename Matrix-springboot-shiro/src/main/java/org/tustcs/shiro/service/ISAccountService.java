package org.tustcs.shiro.service;

import org.tustcs.shiro.entity.AuthRole;
import org.tustcs.shiro.entity.AuthUser;
import org.tustcs.shiro.pojo.JwtRes;

import java.util.List;

public interface ISAccountService {

    String getRoleByUser(String uid);

    boolean isUserExist(String username, String phone);

    JwtRes login(String username, String password);

    boolean logout(String uid);

    boolean register(AuthUser authUser);

}
