package me.supercube.system.app.user.repository;

import me.supercube.system.app.user.model.Sysuser;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by chenping on 16/8/25.
 */
public interface SysuserRepository extends JpaRepository<Sysuser, Long>, JpaSpecificationExecutor<Sysuser> {


    List<Sysuser> findByUserid(String userid) throws DataAccessException;

    List<Sysuser> findByType(String type) throws DataAccessException;

    Long countByUseridAndSiteid(String userid,String siteid) throws DataAccessException;
}
