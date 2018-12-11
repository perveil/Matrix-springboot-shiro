package org.tustcs.shiro.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tustcs.shiro.config.Config;
import org.tustcs.shiro.dao.AuthResourceDAO;
import org.tustcs.shiro.entity.AuthResource;
import org.tustcs.shiro.entity.AuthResourceExample;
import org.tustcs.shiro.entity.AuthRoleExample;
import org.tustcs.shiro.exception.SUserException;
import org.tustcs.shiro.pojo.enums.StatusEnum;
import org.tustcs.shiro.pojo.rules.JwtRule;
import org.tustcs.shiro.service.ISResourceService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Lin
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ShiroResourceServiceImpl implements ISResourceService {

    @Resource
    private AuthResourceDAO authResourceDAO;

    @Override
    public List<JwtRule> getResourceUrlAndRoles() {
        return authResourceDAO.selectUrlAndRole();
    }

    @Override
    public String getAvailMenus(String uid) {
        return authResourceDAO.selectAvailMenus(uid);
    }

    @Override
    public boolean addResource(AuthResource resource) {
        AuthResourceExample example = new AuthResourceExample();
        example.createCriteria().andCodeEqualTo(resource.getCode());
        if (authResourceDAO.selectByExample(example).size() > 0) {
            throw new SUserException(StatusEnum.SRC_EXIST);
        }
        return authResourceDAO.insertSelective(resource) > 0;
    }

    @Override
    public boolean updateStatus(String resourceIds, short status) {
        List<Integer> idList = new LinkedList<>();
        String[] ids = resourceIds.split(",");
        for (String id : ids) {
            idList.add(Integer.parseInt(id));
        }
        AuthResourceExample example1 = new AuthResourceExample();
        example1.createCriteria().andIdIn(idList);
        AuthResource resource = new AuthResource();
        resource.setStatus(status);
        return authResourceDAO.updateByExampleSelective(resource, example1) > 0;
    }

    @Override
    public boolean updateResource(AuthResource resource) {
        return authResourceDAO.updateByPrimaryKeySelective(resource) > 0;
    }

    @Override
    public PageInfo<AuthResource> getResourceList(Integer page) {
        PageHelper.startPage(page, Config.pageNum);
        return new PageInfo<>(authResourceDAO.selectByExample(new AuthResourceExample()));
    }
}
