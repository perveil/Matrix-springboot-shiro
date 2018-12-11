package org.tustcs.shiro.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tustcs.shiro.config.Config;
import org.tustcs.shiro.dao.AuthResourceDAO;
import org.tustcs.shiro.dao.AuthRoleDAO;
import org.tustcs.shiro.dao.AuthRoleResourceDAO;
import org.tustcs.shiro.entity.*;
import org.tustcs.shiro.exception.SUserException;
import org.tustcs.shiro.pojo.enums.StatusEnum;
import org.tustcs.shiro.service.ISRoleService;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lin
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ShiroRoleServiceImpl implements ISRoleService {

    @Autowired
    private AuthRoleDAO         authRoleDAO;

    @Autowired
    private AuthResourceDAO     authResourceDAO;

    @Autowired
    private AuthRoleResourceDAO authRoleResourceDAO;

    @Override
    public PageInfo<AuthRole> getRoleList(Integer page) {
        PageHelper.startPage(page, Config.pageNum);
        return new PageInfo<>(authRoleDAO.selectByExample(new AuthRoleExample()));
    }

    @Override
    public List<AuthResource> getApiByRole(Integer roleId) {
        return authResourceDAO.selectApiByRoleId(roleId);
    }

    @Override
    public boolean authRoleResource(Integer roleId, String resources) {
        String[] resourceIds = resources.split(",");
        List<AuthRoleResource> list = new LinkedList<>();
        for (String resourceId : resourceIds) {
            list.add(new AuthRoleResource(roleId, Integer.parseInt(resourceId)));
        }
        return authRoleResourceDAO.batchInsert(list) > 0;
    }

    @Override
    public boolean updateStatus(String roleIds, Short status) {
        String[] roles = roleIds.split(",");
        List<Integer> ids = new LinkedList<>();
        for (String roleId : roles) {
            ids.add(Integer.parseInt(roleId));
        }
        AuthRoleExample example = new AuthRoleExample();
        example.createCriteria().andIdIn(ids);
        AuthRole role = new AuthRole();
        role.setStatus(status);
        return authRoleDAO.updateByExampleSelective(role, example) > 0;
    }

    @Override
    public boolean addRole(AuthRole authRole) {
        String code = authRole.getCode();
        AuthRoleExample example = new AuthRoleExample();
        example.createCriteria().andCodeEqualTo(code);
        if (authRoleDAO.selectByExample(example).size() > 0) {
            throw new SUserException(StatusEnum.ROLE_EXIST);
        }
        return authRoleDAO.insertSelective(authRole) > 0;
    }

    @Override
    public boolean updateRole(AuthRole authRole) {
        return authRoleDAO.updateByPrimaryKeySelective(authRole) > 0;
    }

    @Override
    public List<AuthResource> getUnauthResource(Integer roleId) {
        return authResourceDAO.selectUnauthResource(roleId);
    }

    @Override
    public boolean deleteRoleResource(Integer roleId, String resources) {
        String[] resourceIds = resources.split(",");
        List<Integer> idList = new LinkedList<>();
        for (String resourceId : resourceIds) {
            idList.add(Integer.parseInt(resourceId));
        }
        AuthRoleResourceExample example = new AuthRoleResourceExample();
        example.createCriteria().andRoleIdEqualTo(roleId).andResourceIdIn(idList);
        return authRoleResourceDAO.deleteByExample(example) > 0;
    }
}
