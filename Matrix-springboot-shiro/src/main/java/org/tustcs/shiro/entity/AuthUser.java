package org.tustcs.shiro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * auth_user
 * @author
 */
public class AuthUser implements Serializable {
    /**
     * 用户主键
     */
    private String            uid;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String            username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String            password;

    /**
     * 盐
     */
    @JsonIgnore
    private String            salt;

    /**
     * 真名
     */
    @NotBlank(message = "昵称不能为空哦")
    private String            realName;

    /**
     * 头像
     */
    private String            avatar;

    /**
     * 电话号码（唯一)
     */
    @NotBlank(message = "手机号不能为空")
    private String            phone;

    /**
     * 邮箱（唯一）
     */
    private String            email;

    /**
     * 性别（1 男 2 女）
     */
    private Byte              sex;

    /**
     * 账户状态（1.正常 2.锁定 3.删除 4.非法）
     */
    @JsonIgnore
    private Byte              status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date              createTime;

    /**
     * 更新时间
     */
    @JsonIgnore
    private Date              updateTime;

    private static final long serialVersionUID = 1L;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        AuthUser other = (AuthUser) that;
        return (this.getUid() == null ? other.getUid() == null : this.getUid().equals(other.getUid()))
               && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
               && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
               && (this.getSalt() == null ? other.getSalt() == null : this.getSalt().equals(other.getSalt()))
               && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
               && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
               && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
               && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
               && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
               && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUid() == null) ? 0 : getUid().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getSalt() == null) ? 0 : getSalt().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uid=").append(uid);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", salt=").append(salt);
        sb.append(", realName=").append(realName);
        sb.append(", avatar=").append(avatar);
        sb.append(", phone=").append(phone);
        sb.append(", email=").append(email);
        sb.append(", sex=").append(sex);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
