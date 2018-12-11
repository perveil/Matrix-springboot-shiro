package org.tustcs.shiro.service;

import com.github.pagehelper.PageInfo;
import org.tustcs.shiro.entity.AuthResource;
import org.tustcs.shiro.entity.AuthRole;

import java.util.List;

public interface ISRoleService {
    PageInfo<AuthRole> getRoleList(Integer page);

    List<AuthResource> getApiByRole(Integer roleId);

    boolean authRoleResource(Integer roleId, String resources);

    boolean updateStatus(String roleIds, Short status);

    boolean addRole(AuthRole authRole);

    boolean updateRole(AuthRole authRole);

    List<AuthResource> getUnauthResource(Integer roleId);

    boolean deleteRoleResource(Integer roleId, String resources);
}
