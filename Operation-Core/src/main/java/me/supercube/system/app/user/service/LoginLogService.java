package me.supercube.system.app.user.service;

import me.supercube.system.app.user.model.LoginLog;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

public interface LoginLogService {


    /**
     * 保存用户登陆日志
     *
     * @param entity 登陆日志对象
     * @return 登陆日志
     */
    LoginLog save(LoginLog entity);


    /**
     * 查找指定查询条件的用户
     *
     * @param userid   用户ID
     * @param pageable 分页对象
     * @return {@link LoginLog} paging data
     */
    Page<LoginLog> findAll(String userid, LocalDateTime starttime, LocalDateTime endtime, Pageable pageable);

    /**
     * 删除指定日期之后的用户日志
     *
     * @param loginid   登陆ID
     * @param starttime 开始日期
     * @return
     */
    boolean cleanup(String loginid, LocalDateTime starttime) throws Exception;

    /**
     * 导出数据到EXCEL
     *
     * @param loginid   登陆ID
     * @param starttime 登陆时间
     * @param endtime   登陆时间
     */
    String exportToExcel(String loginid, LocalDateTime starttime, LocalDateTime endtime, HttpServletRequest request,
                         HttpServletResponse response);
}
