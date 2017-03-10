package me.supercube.security.model;

import javax.persistence.*;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * 角色表
 * Created by wangdz on 2016/11/8.
 */
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = {"roleCode"}))
public class Role implements Serializable {
    private Long roleid; //唯一标识
    private String roleName; //角色名称
    private String roleCode; //角色编码
    private String description; //角色描述

    private String userType; //角色所对应的用户类型

    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "roleid", unique = true, nullable = false)
    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }

    @Column(name = "roleCode", unique = true, nullable = false)
    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    @Column(name = "roleName" ,nullable = false)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "userType")
    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
