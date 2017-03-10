package me.supercube.security.repository;

import me.supercube.security.model.Permission;
import me.supercube.security.model.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 权限信息
 * Created by wangdz on 2016/9/14.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long>,JpaSpecificationExecutor<Permission> {

    List<Permission> findByRoleCode(String roleCode) throws DataAccessException;

}
