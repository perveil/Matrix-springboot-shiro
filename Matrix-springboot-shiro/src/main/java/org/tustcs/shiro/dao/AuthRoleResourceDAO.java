package org.tustcs.shiro.dao;

import org.springframework.stereotype.Repository;
import org.tustcs.shiro.entity.AuthRoleResource;
import org.tustcs.shiro.entity.AuthRoleResourceExample;

import java.util.List;

/**
 * AuthRoleResourceDAO继承基类
 */
@Repository
public interface AuthRoleResourceDAO extends MyBatisBaseDao<AuthRoleResource, AuthRoleResource, AuthRoleResourceExample> {

    int batchInsert(List<AuthRoleResource> list);
}
