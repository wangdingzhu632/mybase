package me.supercube.system.app.user.web;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.supercube.common.model.Message;
import me.supercube.common.model.Message4Page;
import me.supercube.system.app.user.model.LoginLog;
import me.supercube.system.app.user.service.LoginLogService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/log/login")
public class LoginLogController {
	private final static Log logger=LogFactory.getLog(LoginLogController.class);
    private final DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Message4Page<LoginLog> showLoginLogs(
            @RequestParam(value = "filters", required = false) String filters,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "starttime", required = false) String starttime,
            @RequestParam(value = "endtime", required = false) String endtime) {


        LocalDateTime dtStart = null, dtEnd= null;
        if (StringUtils.hasLength(starttime)) {
            dtStart =  LocalDateTime.parse(starttime,dtf);
        }
        if (StringUtils.hasLength(endtime)) {
            dtEnd =  LocalDateTime.parse(endtime,dtf);
        }

        Sort sort = new Sort(Sort.Direction.DESC, "loginlogid");
        Pageable pageable = new PageRequest(page - 1, rows, sort);

        Page<LoginLog> results = null;
        Message4Page<LoginLog> message = null;
        try {
            results = loginLogService.findAll(loginid, dtStart, dtEnd, pageable);
            message = new Message4Page<LoginLog>(Message.success(), results);
        } catch (Exception e) {
            logger.error("查询失败", e);
            message = new Message4Page<LoginLog>(Message.fail(e.toString()), results);

        }

        return message;
    }

    /**
     * 导出日志数据
     *
     * @param loginid   登陆ID
     * @param starttime 日志开始日期
     * @param endtime   日志结束日期
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public void exportToExcel(
            @RequestParam(value = "loginid", required = false) String loginid,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "starttime", required = false) String starttime,
            @RequestParam(value = "endtime", required = false) String endtime,
            HttpServletRequest request, HttpServletResponse response) {

        BufferedInputStream in = null;
        BufferedOutputStream out = null;

        try {
            request.setCharacterEncoding("UTF-8");

            LocalDateTime dtStart = null, dtEnd= null;
            if (StringUtils.hasLength(starttime)) {
                dtStart =  LocalDateTime.parse(starttime,dtf);
            }
            if (StringUtils.hasLength(endtime)) {
                dtEnd =  LocalDateTime.parse(endtime,dtf);
            }


            String filePath = loginLogService.exportToExcel(loginid, dtStart, dtEnd, request, response);


            File file = new File(filePath);
            String fileName = file.getName();
            fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
            System.out.println(fileName);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setHeader("Content-Length", String.valueOf(file.length()));

            in = new BufferedInputStream(new FileInputStream(file));
            out = new BufferedOutputStream(response.getOutputStream());
            byte[] data = new byte[1024];
            int len = 0;
            while (-1 != (len = in.read(data, 0, data.length))) {
                out.write(data, 0, len);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("不支持的字符转码", e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("文件未找到异常", e);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("IOException", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("IOException", e);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("IOException", e);
                }
            }
        }


    }

}
