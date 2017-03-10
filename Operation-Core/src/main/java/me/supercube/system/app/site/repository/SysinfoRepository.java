package me.supercube.system.app.site.repository;

import me.supercube.system.app.site.model.Sysinfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 系统信息Repository
 * Created by wangdz on 2016/9/21.
 */
public interface SysinfoRepository extends JpaRepository<Sysinfo, String>,JpaSpecificationExecutor<Sysinfo> {

}
