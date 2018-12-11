package org.tustcs.shiro.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tustcs.shiro.entity.AuthUserRole;
import org.tustcs.shiro.entity.AuthUserRoleExample;

import java.util.List;

/**
 * AuthUserRoleDAO继承基类
 */
@Repository
public interface AuthUserRoleDAO extends MyBatisBaseDao<AuthUserRole, Integer, AuthUserRoleExample> {
    int batchInsert(List<AuthUserRole> list);
}
