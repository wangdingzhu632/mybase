package me.supercube.security.service.impl;

import me.supercube.security.model.Permission;
import me.supercube.security.repository.PermissionRepository;
import me.supercube.security.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangdz on 2016/11/8.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public List<String> findByRoleCode(String roleCode) throws Exception {
        List<String> permissionList = new ArrayList<String>();
        List<Permission> list = permissionRepository.findByRoleCode(roleCode);
        for(Permission permission : list){
            permissionList.add(permission.getUrl());
        }
        return  permissionList;
    }

    @Override
    public List<Permission> findPermissionByRoleCode(String role) throws Exception {
        List<Permission> list = permissionRepository.findByRoleCode(role);
        return list;
    }
}
