package org.tustcs.shiro.service;

import org.tustcs.shiro.entity.AuthResource;
import org.tustcs.shiro.entity.AuthUser;
import org.tustcs.shiro.pojo.UserInfo;

import java.util.List;

public interface ISUserService {
    List<AuthUser> getUserList(Integer page);

    boolean addUser(AuthUser authUser);

    boolean updateStatus(String uid, Byte status);

    boolean updateUser(AuthUser authUser);

    AuthUser getUser(String uid);

    UserInfo getUserInfo(String uid);

    String getApiByUser(String uid);

    String getMenuByUser(String uid);

    boolean authRole(String uid, String roles);
}
