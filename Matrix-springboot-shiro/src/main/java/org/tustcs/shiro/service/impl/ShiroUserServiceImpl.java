package org.tustcs.shiro.service.impl;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tustcs.shiro.config.Config;
import org.tustcs.shiro.dao.AuthResourceDAO;
import org.tustcs.shiro.dao.AuthRoleDAO;
import org.tustcs.shiro.dao.AuthUserDAO;
import org.tustcs.shiro.dao.AuthUserRoleDAO;
import org.tustcs.shiro.entity.AuthResource;
import org.tustcs.shiro.entity.AuthUser;
import org.tustcs.shiro.entity.AuthUserExample;
import org.tustcs.shiro.entity.AuthUserRole;
import org.tustcs.shiro.exception.SUserException;
import org.tustcs.shiro.pojo.UserInfo;
import org.tustcs.shiro.pojo.enums.StatusEnum;
import org.tustcs.shiro.service.ISUserService;
import org.tustcs.shiro.util.UUIDUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * @author Lin
 */
@Service
@Transactional
@Slf4j
public class ShiroUserServiceImpl implements ISUserService {

    @Autowired
    private AuthResourceDAO authResourceDAO;

    @Autowired
    private AuthUserDAO     authUserDAO;

    @Autowired
    private AuthRoleDAO     authRoleDAO;

    @Autowired
    private AuthUserRoleDAO authUserRoleDAO;

    @Override
    public List<AuthUser> getUserList(Integer page) {
        PageHelper.startPage(page, Config.pageNum);
        return authUserDAO.selectByExample(new AuthUserExample());
    }

    @Override
    public boolean addUser(AuthUser authUser) {
        authUser.setUid(UUIDUtils.getUUID());
        return authUserDAO.insertSelective(authUser) > 0;
    }

    @Override
    public boolean updateStatus(String uids, Byte status) {
        List<String> uidList = Arrays.asList(uids.split(","));
        AuthUserExample example = new AuthUserExample();
        example.createCriteria().andUidIn(uidList);
        AuthUser authUser = new AuthUser();
        authUser.setStatus(status);
        return authUserDAO.updateByExampleSelective(authUser, example) > 0;
    }

    @Override
    public boolean updateUser(AuthUser authUser) {
        return authUserDAO.updateByPrimaryKeySelective(authUser) > 0;
    }

    @Override
    public String getApiByUser(String uid) {
        return authResourceDAO.selectResources(uid, (short) 2);
    }

    @Override
    public String getMenuByUser(String uid) {
        return authResourceDAO.selectResources(uid, (short) 2);
    }

    @Override
    public AuthUser getUser(String uid) {
        return authUserDAO.selectByPrimaryKey(uid);
    }

    @Override
    public UserInfo getUserInfo(String uid) {
        AuthUser authUser = authUserDAO.selectByPrimaryKey(uid);
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(authUser, userInfo);
        userInfo.setRoles(authRoleDAO.selectRolesByUid(uid));
        return userInfo;
    }

    @Override
    public boolean authRole(String uid, String roles) {
        String[] roleIds = roles.split(",");
        List<AuthUserRole> userRoleList = new LinkedList<>();
        for (String roleId : roleIds) {
            userRoleList.add(new AuthUserRole(uid, Integer.parseInt(roleId)));
        }
        return authUserRoleDAO.batchInsert(userRoleList) > 0;
    }
}
