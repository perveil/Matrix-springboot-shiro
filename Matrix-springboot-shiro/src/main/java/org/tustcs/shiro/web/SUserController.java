package org.tustcs.shiro.web;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tustcs.shiro.entity.AuthUser;
import org.tustcs.shiro.pojo.ResponseBean;
import org.tustcs.shiro.service.ISUserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/suser")
@Slf4j
public class SUserController extends SBaseController {

    @Autowired
    private ISUserService userService;

    @PostMapping("/getList")
    public ResponseBean getUserList(Integer page) {
        return new ResponseBean().ok().addData("userList", userService.getUserList(page));
    }

    @PostMapping("/getInfo")
    public ResponseBean getUserInfo(String userId) {
        return new ResponseBean().ok().addData("user", userService.getUserInfo(userId));
    }

    @PostMapping("/getMenu")
    public ResponseBean getMenu(HttpServletRequest request) {
        return new ResponseBean().ok().addData("menuList", userService.getMenuByUser(request.getHeader("Uid")));
    }

    @PostMapping("/getApi")
    public ResponseBean getApi(String userId) {
        return new ResponseBean().ok().addData("apiList", userService.getApiByUser(userId));
    }

    @PostMapping("/add")
    public ResponseBean addUser(@Validated @RequestBody AuthUser authUser, BindingResult errors) {
        handleError(errors);
        if (userService.addUser(authUser)) {
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/update")
    public ResponseBean updateUser(@Validated @RequestBody AuthUser authUser, BindingResult errors) {
        handleError(errors);
        if (userService.updateUser(authUser)) {
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/updateStatus")
    public ResponseBean updateStatus(String userIds, Byte status) {
        if (userService.updateStatus(userIds, status)) {
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }

    @PostMapping("/authRole")
    public ResponseBean authRole(String userId, String roles) {
        if (userService.authRole(userId, roles)) {
            return new ResponseBean().ok();
        }
        return new ResponseBean().error();
    }
}
