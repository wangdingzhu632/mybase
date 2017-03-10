package me.supercube.security.service;

import me.supercube.security.model.Permission;

import java.util.List;

/**
 * 权限service
 * Created by wangdz on 2016/11/8.
 */
public interface PermissionService {
    //根据角色查询对应的权限
    List<String> findByRoleCode(String role) throws Exception;

    List<Permission> findPermissionByRoleCode(String role) throws Exception;

}
