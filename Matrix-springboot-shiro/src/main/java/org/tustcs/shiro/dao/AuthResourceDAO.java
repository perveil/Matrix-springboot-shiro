package org.tustcs.shiro.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.tustcs.shiro.entity.AuthResource;
import org.tustcs.shiro.entity.AuthResourceExample;
import org.tustcs.shiro.pojo.rules.JwtRule;

import java.util.List;
import java.util.Set;

/**
 * AuthResourceDAO继承基类
 */
@Repository
public interface AuthResourceDAO extends MyBatisBaseDao<AuthResource, Integer, AuthResourceExample> {
    String selectResources(@Param("uid") String uid, @Param("type") short type);

    List<AuthResource> selectUnauthResource(@Param("roleId") Integer roleId);

    List<JwtRule> selectUrlAndRole();

    String selectAvailMenus(@Param("uid") String uid);

    List<AuthResource> selectApiByRoleId(@Param("roleId") Integer roleId);

}
