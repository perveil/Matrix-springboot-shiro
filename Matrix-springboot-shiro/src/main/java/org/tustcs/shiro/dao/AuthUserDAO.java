package org.tustcs.shiro.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tustcs.shiro.entity.AuthUser;
import org.tustcs.shiro.entity.AuthUserExample;

/**
 * AuthUserDAO继承基类
 * @author Lin
 */
public interface AuthUserDAO extends MyBatisBaseDao<AuthUser, String, AuthUserExample> {

    String selectRoles(@Param("uid") String uid);

}
