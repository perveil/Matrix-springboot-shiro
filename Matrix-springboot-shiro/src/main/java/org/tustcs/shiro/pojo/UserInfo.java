package org.tustcs.shiro.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class UserInfo {

    /**
     * 用户主键
     */
    private String uid;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 盐
     */
    @JsonIgnore
    private String salt;

    /**
     * 真名
     */
    @NotBlank(message = "昵称不能为空")
    private String realName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 电话号码（唯一)
     */
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /**
     * 邮箱（唯一）
     */
    private String email;

    /**
     * 性别（1 男 2 女）
     */
    private Byte   sex;

    /**
     * 账户状态（1.正常 2.锁定 3.删除 4.非法）
     */
    @JsonIgnore
    private Byte   status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date   createTime;

    private String roles;
}
