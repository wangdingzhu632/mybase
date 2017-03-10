package me.supercube.security.repository;

import me.supercube.security.model.Role;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.List;

/**
 * 角色信息
 * Created by wangdz on 2016/9/14.
 */
public interface RoleRepository extends JpaRepository<Role, Long>,JpaSpecificationExecutor<Role> {
    /**
     * 根据用户类型查询用户角色
     * @param userType
     * @return
     * @throws DataAccessException
     */
    List<Role> findByUserType(String userType) throws DataAccessException;

}
