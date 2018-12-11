package org.tustcs.shiro.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tustcs.shiro.entity.AuthRole;
import org.tustcs.shiro.entity.AuthRoleExample;

/**
 * AuthRoleDAO继承基类
 */
@Repository
public interface AuthRoleDAO extends MyBatisBaseDao<AuthRole, Integer, AuthRoleExample> {
    String selectRolesByUid(@Param("uid") String uid);
}
