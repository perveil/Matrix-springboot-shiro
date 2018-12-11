package org.tustcs.shiro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * auth_resource
 * @author
 */
public class AuthResource implements Serializable {
    /**
     * 资源id
     */
    private Integer           id;

    /**
     * 资源名称
     */
    @NotBlank(message = "名称不能为空")
    private String            code;

    /**
     * 资源描述
     */
    @NotBlank(message = "描述不能为空")
    private String            name;

    /**
     * 父资源id
     */
    private Integer           parentId;

    /**
     * 资源uri
     */
    @NotBlank(message = "uri不能为空")
    private String            uri;

    /**
     * 类型 1:菜单menu 2:资源element(rest-api) 3:资源分类
     */
    private Short             type;

    /**
     * 访问方式 （GET POST）
     */
    @NotBlank(message = "访问方法不能为空")
    private String            method;

    /**
     * 图标
     */
    private String            icon;

    /**
     * 状态 1：正常 2：禁用
     */
    private Short             status;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
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
        AuthResource other = (AuthResource) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId())) && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
               && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
               && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
               && (this.getUri() == null ? other.getUri() == null : this.getUri().equals(other.getUri())) && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
               && (this.getMethod() == null ? other.getMethod() == null : this.getMethod().equals(other.getMethod()))
               && (this.getIcon() == null ? other.getIcon() == null : this.getIcon().equals(other.getIcon()))
               && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
               && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
               && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getUri() == null) ? 0 : getUri().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getMethod() == null) ? 0 : getMethod().hashCode());
        result = prime * result + ((getIcon() == null) ? 0 : getIcon().hashCode());
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
        sb.append(", id=").append(id);
        sb.append(", code=").append(code);
        sb.append(", name=").append(name);
        sb.append(", parentId=").append(parentId);
        sb.append(", uri=").append(uri);
        sb.append(", type=").append(type);
        sb.append(", method=").append(method);
        sb.append(", icon=").append(icon);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
