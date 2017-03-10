package me.supercube.security.service.impl;

import me.supercube.security.model.Role;
import me.supercube.security.repository.RoleRepository;
import me.supercube.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息service
 * Created by wangdz on 2016/11/8.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<String> findByUserType(String userType) throws Exception {
        List<String> roleList = new ArrayList<String>();
        List<Role> list = roleRepository.findByUserType(userType);
        for(Role role : list){
            roleList.add(role.getRoleCode());
        }
        return roleList;
    }
}
