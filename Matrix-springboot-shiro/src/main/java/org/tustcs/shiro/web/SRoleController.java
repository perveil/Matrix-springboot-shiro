package org.tustcs.shiro.web;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tustcs.shiro.entity.AuthRole;
import org.tustcs.shiro.exception.SUserException;
import org.tustcs.shiro.pojo.ResponseBean;
import org.tustcs.shiro.service.ISRoleService;
import org.tustcs.shiro.shiro.filter.FilterManager;

@RestController
@RequestMapping("/srole")
@Slf4j
public class SRoleController extends SBaseController {

    @Autowired
    private ISRoleService roleService;

    @Autowired
    private FilterManager filterManager;

    @PostMapping("/getList")
    public ResponseBean getRoleList(Integer page) {
        return new ResponseBean().ok().addData("roleList", roleService.getRoleList(page));
    }

    @PostMapping("/getSrc")
    public ResponseBean getApiByRole(Integer roleId) {
        return new ResponseBean().ok().addData("apiList:", roleService.getApiByRole(roleId));
    }

    @PostMapping("/getUnauthSrc")
    public ResponseBean getUnauthSrc(Integer roleId) {
        return new ResponseBean().ok().addData("apiList", roleService.getUnauthResource(roleId));
    }

    @PostMapping("/updateStatus")
    public ResponseBean updateStatus(String roleIds, Short status) {
        roleService.updateStatus(roleIds, status);
        return new ResponseBean().ok();
    }

    @PostMapping("/add")
    public ResponseBean addRole(@Validated @RequestBody AuthRole authRole, BindingResult errors) {
        log.info("角色：{}", authRole);
        handleError(errors);
        if (roleService.addRole(authRole)) {
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/update")
    public ResponseBean updateRole(@Validated @RequestBody AuthRole authRole, BindingResult errors) {
        log.info("角色：{}", authRole);
        handleError(errors);
        if (roleService.updateRole(authRole)) {
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/authRoleResource")
    public ResponseBean authRoleResource(Integer roleId, String resources) {
        if (roleService.authRoleResource(roleId, resources)) {
            filterManager.reloadFilterChain();
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/deleteRoleResource")
    public ResponseBean deleteRoleResource(Integer roleId, String resources) {
        if (roleService.deleteRoleResource(roleId, resources)) {
            filterManager.reloadFilterChain();
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

}
