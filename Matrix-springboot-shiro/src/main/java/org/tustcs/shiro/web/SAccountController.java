package org.tustcs.shiro.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.tustcs.shiro.entity.AuthUser;
import org.tustcs.shiro.exception.SUserException;
import org.tustcs.shiro.pojo.JwtRes;
import org.tustcs.shiro.pojo.ResponseBean;
import org.tustcs.shiro.pojo.enums.StatusEnum;
import org.tustcs.shiro.service.ISAccountService;
import org.tustcs.shiro.util.JWTUtil;
import org.tustcs.shiro.util.MD5Utils;
import org.tustcs.shiro.util.RequestResponseUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/saccount")
@Slf4j
public class SAccountController extends SBaseController {

    @Resource
    private ISAccountService accountService;

    @PostMapping("/login")
    public ResponseBean login(@RequestParam("username") String username, @RequestParam("password") String password) {
        JwtRes res = accountService.login(username, password);
        return new ResponseBean().ok("login success").addData("jwt", res.getJwt()).addData("uid", res.getUid()).addData("menus", res.getMenus());
    }

    @PostMapping("/logout")
    public ResponseBean logout(HttpServletRequest request) {
        String uid = request.getHeader("Uid");
        if (accountService.logout(uid)) {
            return new ResponseBean().ok("logout success");
        }
        return new ResponseBean().error();
    }

    @PostMapping("/register")
    public ResponseBean register(@Validated @RequestBody AuthUser authUser, BindingResult errors) {
        log.info("用户：{}", authUser);
        handleError(errors);
        if (!accountService.isUserExist(authUser.getUsername(), authUser.getPhone())) {
            throw new SUserException(StatusEnum.USER_EXIST);
        }
        if (accountService.register(authUser)) {
            return new ResponseBean().ok("reg success");
        }
        return new ResponseBean().error();
    }
}
