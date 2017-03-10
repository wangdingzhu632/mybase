package me.supercube.security.web;


import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import me.supercube.common.model.Message;
import me.supercube.common.model.Message4Entity;
import me.supercube.security.model.CaptchaUsernamePasswordToken;
import me.supercube.security.util.Des;
import me.supercube.system.app.user.model.LoginLog;
import me.supercube.system.app.user.model.Sysuser;
import me.supercube.system.app.user.service.LoginLogService;
import me.supercube.system.app.user.service.SysuserService;
import me.supercube.security.util.LoginUserUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 系统登录校验
 */
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final static Log logger = LogFactory
            .getLog(AuthenticationController.class);

    @Autowired
    private SysuserService sysuserService;

    @Autowired
    private LoginLogService loginLogService;

    private String clientip = "";


    private Message4Entity<LoginLog> checkLogin(Sysuser currUser,
                                                HttpServletRequest request, Model model) {
        String error = "";
        String loginPassword = currUser.getPassword();
        LoginLog loginLog = new LoginLog();
        // 获取当前的Subject
        Subject subject = SecurityUtils.getSubject();
        try {
            // 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
            // 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
            // 所以这一步在调用login(token)方法时,它会走到ShiroDbRealm.doGetAuthenticationInfo()方法中
            Session session = subject.getSession(true);
            boolean rememberMe = false;
            if (request.getParameter("rememberMe") != null) {
                rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
            }
            //数据库查询用户
            currUser = sysuserService.findByUserid(currUser.getLoginid());
            if(null == currUser){
                throw new UnknownAccountException();
            }
            //用户类型
            String userType = currUser.getType();
            CaptchaUsernamePasswordToken token = new CaptchaUsernamePasswordToken(
                    currUser.getLoginid(),loginPassword, rememberMe, userType);

            //设置最后登录时间
            currUser.setLastevaldate(LocalDateTime.now());
            //session.setAttribute("currUser",currUser);
            session.setAttribute("userid",currUser);
            //登录校验
            subject.login(token);

            // 添加系统信息到上下文中
//			ServletContext context = request.getServletContext();
//			context.setAttribute("SYSINFO", sysInfo);
            LoginUserUtil.setUser(currUser);
            loginLog.setUserid(currUser.getLoginid());
            loginLog.setUserType(currUser.getType());

            loginLog.setSessionuid(session.getId().toString());
            return new Message4Entity<LoginLog>(Message.success(), loginLog);
        } catch (Exception e) {
            // 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
            String exceptionClassName = e.getClass().getName();
            if (UnknownAccountException.class.getName().equals(
                    exceptionClassName)) {
                error = "用户名为" + currUser.getLoginid() + "不存在";
            } else if (IncorrectCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                error = "用户名/密码错误";
            } else if (LockedAccountException.class.getName().equals(
                    exceptionClassName)) {
                error = String.format("用户名为[%s]的账户锁定，请联系管理员。");
            } else if (DisabledAccountException.class.getName().equals(
                    exceptionClassName)) {
                error = String.format("用户名[%s]未审核。");
            } else if (ExcessiveAttemptsException.class.getName().equals(
                    exceptionClassName)) {
                error = String.format("用户名[%s]登录次数过多。");
            } else if (ExpiredCredentialsException.class.getName().equals(
                    exceptionClassName)) {
                error = String.format("用户名[%s]的用户凭证过期。");
            } else if (AuthenticationException.class.getName().equals(
                    exceptionClassName)) {
                error = String.format("用户名[%s]验证失败。");
            } else if (exceptionClassName != null) {
                error = "其他错误：" + e.getMessage();
                logger.error(e);
            } else {
                logger.error("其它错误", e);
            }
            try {
                logger.error("登录失败错误信息", e);
                loginLog.setUserid(currUser.getUserid());
                loginLog.setAttemptdate(LocalDateTime.now());
                loginLog.setClientaddr(subject.getSession().getHost());
                loginLog.setLoginid(currUser.getLoginid());
                loginLog.setAttemptresult("登录失败");
                loginLog.setName(currUser.getTruename());
                loginLog.setClienthost(subject.getSession().getHost());
                loginLog.setSessionuid(subject.getSession().getId().toString());
                loginLogService.save(loginLog);
            } catch (Exception ex) {
                logger.error("保存登陆错误日志失败", ex);
            }
            model.addAttribute("error", error);
            request.setAttribute("error", error);

            return new Message4Entity<LoginLog>(Message.fail(error), loginLog);
        }
    }


    /**
     * 验证登陆用户
     *
     * @param currUser 登陆用户信息
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Message4Entity<LoginLog> processLoginForm(Sysuser currUser,
                                                     HttpServletRequest request, Model model) {
        String pwd = currUser.getPassword();
        if(pwd.contains("@")){
            Des des = new Des();
            //des解密
            pwd = pwd.replace("@","");
            String temp = des.strDec(currUser.getPassword(),"1","22","333");
            currUser.setPassword(temp);
        }
        return checkLogin(currUser, request, model);
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Message processLogout() {
        try {
            Subject currentUser = SecurityUtils.getSubject();

           // currentUser.getSession().getAttribute("userid");
            //session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);

            if (null != currentUser && currentUser.isAuthenticated()) {
                LoginLog loginLog = new LoginLog();
                loginLog.setUserid(currentUser.getPrincipal().toString());
                loginLog.setClientaddr(clientip);
                loginLog.setAttemptdate(LocalDateTime.now());
                loginLog.setAttemptresult("退出登录");
                loginLogService.save(loginLog);
                LoginUserUtil.setUser(null);
                currentUser.logout();
            }
            return Message.success();
        } catch (Exception e) {
            logger.error("登出异常", e);
            return Message.fail(e.toString());
        }
    }
}
