package me.supercube.system.app.user.service.impl;


import me.supercube.common.util.ExportExcel;
import me.supercube.system.app.user.model.LoginLog;
import me.supercube.system.app.user.repository.LoginLogRepository;
import me.supercube.system.app.user.service.LoginLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    private static final Log logger = LogFactory.getLog(LoginLogService.class);

    @Autowired
    private LoginLogRepository loginLogRepository;


    @Transactional
    public LoginLog save(LoginLog loginLog) {
        return loginLogRepository.save(loginLog);
    }


    @Override
    public Page<LoginLog> findAll(String userid, LocalDateTime starttime, LocalDateTime endtime, Pageable pageable) {
        Sort sort = new Sort(Direction.DESC, "logid");
        // 显示分页信息
        return loginLogRepository.findAll(getLogWhereClause(userid, starttime, endtime), pageable);
    }

    private Specification<LoginLog> getLogWhereClause(final String userid, final LocalDateTime starttime, final LocalDateTime endtime) {
        return new Specification<LoginLog>() {

            @Override
            public Predicate toPredicate(Root<LoginLog> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {

                Predicate predicate = cb.conjunction();

                if (StringUtils.hasLength(userid)) {
                    predicate.getExpressions().add(
                            cb.equal(root.<String>get("userid"), userid));
                }

                if (starttime != null) {
                    predicate.getExpressions().add(
                            cb.greaterThanOrEqualTo(
                                    root.get("attemptdate"), starttime));
                }
                if (endtime != null) {
                    predicate.getExpressions().add(
                            cb.lessThanOrEqualTo(
                                    root.get("attemptdate"), endtime));
                }

                return predicate;
            }

        };

    }

    @Transactional
    @Override
    public boolean cleanup(String loginid, LocalDateTime starttime) throws Exception {
        if (starttime == null) {
            throw new Exception("请指定目标日期");
        }
        if (StringUtils.hasLength(loginid)) {
            loginLogRepository.deleteByLoginidAndAttemptdateGreaterThan(loginid, starttime);
        } else {
            loginLogRepository.deleteByAttemptdateGreaterThan(starttime);
        }
        return true;
    }


    @Override
    public String exportToExcel(String loginid, LocalDateTime starttime,
                                LocalDateTime endtime, HttpServletRequest request,
                                HttpServletResponse response) {

        //设置文件路径
        String filePath = request.getSession().getServletContext().getRealPath("/excels/"),
                fileName = "LoginLog.xls",
                fullPath = filePath + fileName;
        //生成Excel文件
        FileOutputStream out = null;
        try {
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            Sort sort = new Sort(Direction.DESC, "logid");
            Collection<LoginLog> loginLogs = loginLogRepository.findAll(getLogWhereClause(loginid, starttime, endtime), sort);


            out = new FileOutputStream(fullPath);

            String[] headers = new String[]{"ID", "用户ID", "用户名", "操作时间", "消息类型", "IP", "日志内容"};
            String[] fields = new String[]{"logid", "loginid", "displayname", "logtime", "clientip", "remark"};

            ExportExcel<LoginLog> export = new ExportExcel<LoginLog>();
            export.exportExcel("用户日志", headers, fields, loginLogs, out, "");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error(e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        } finally {
            try {
                if (out != null)
                    out.close();
                logger.info("导出表格创建成功");
            } catch (Exception io) {
                io.printStackTrace();
            }
        }
        return fullPath;
    }


}
