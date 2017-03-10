package me.supercube.system.app.user.repository;


import me.supercube.system.app.user.model.LoginLog;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface LoginLogRepository extends JpaRepository<LoginLog, Long>,
		JpaSpecificationExecutor<LoginLog> {

	/**
	 * 删除指定用户的日志数据
	 *
	 * @param loginid
	 *
	 * @param logtime
	 * */
	@Modifying
	@Query("delete from LoginLog where loginid=?1 and attemptdate>=?2")
	void deleteByLoginidAndAttemptdateGreaterThan(String loginid, LocalDateTime logtime);

	/**
	 * 删除指定日期的日志数据
	 *
	 * @param logtime
	 * */
	@Modifying
	@Query("delete from LoginLog where attemptdate>=?1")
	void deleteByAttemptdateGreaterThan(LocalDateTime logtime) throws DataAccessException;

}
