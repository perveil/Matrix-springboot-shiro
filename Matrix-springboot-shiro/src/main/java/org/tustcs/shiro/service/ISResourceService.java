package org.tustcs.shiro.service;

import com.github.pagehelper.PageInfo;
import org.tustcs.shiro.entity.AuthResource;
import org.tustcs.shiro.pojo.rules.JwtRule;

import java.util.List;

public interface ISResourceService {

    List<JwtRule> getResourceUrlAndRoles();

    boolean addResource(AuthResource resource);

    boolean updateStatus(String resourceIds, short status);

    boolean updateResource(AuthResource resource);

    String getAvailMenus(String uid);

    PageInfo<AuthResource> getResourceList(Integer page);

}
