package me.supercube.security.service;

import me.supercube.security.model.Role;

import java.util.List;

/**
 * 角色Service
 * Created by wangdz on 2016/11/8.
 */
public interface RoleService {
    //根据用户类型查询用户所拥有的角色
    List<String> findByUserType(String userType) throws Exception;

}
