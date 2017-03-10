package me.supercube.security.model;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;
/**
 * 权限表
 * Created by wangdz on 2016/11/8.
 */
@Entity
@Table(name = "permission", uniqueConstraints = @UniqueConstraint(columnNames = {"permissionCode"}))
public class Permission implements Serializable {
    private Long permissionid; //唯一标识
    private String permissionCode; //权限编号
    private String url; //操作url
    private String description; //权限描述

    private String roleCode; //所属角色

    private String parent; //父级菜单

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "permissionid", unique = true, nullable = false)
    public Long getPermissionid() {
        return permissionid;
    }

    public void setPermissionid(Long permissionid) {
        this.permissionid = permissionid;
    }

    @Column(name = "permissionCode", unique = true, nullable = false)
    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "roleCode")
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Column(name = "parent")
    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
