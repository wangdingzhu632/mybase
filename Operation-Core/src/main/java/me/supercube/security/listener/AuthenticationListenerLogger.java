/**
 * Copyright 2013-2020 the original author or authors.
 */
package me.supercube.security.listener;


import me.supercube.system.app.user.model.LoginLog;
import me.supercube.system.app.user.model.Sysuser;
import me.supercube.system.app.user.service.LoginLogService;
import me.supercube.system.app.user.service.SysuserService;
import me.supercube.security.util.LoginUserUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.time.LocalDateTime;
import java.util.Enumeration;

/**
 * @author chenping
 */
public class AuthenticationListenerLogger implements AuthenticationListener {

    private static final Logger log = LoggerFactory
            .getLogger(AuthenticationListenerLogger.class);

    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private SysuserService sysuserService;

    @Override
    public void onSuccess(AuthenticationToken token, AuthenticationInfo info) {
        log.info("Login success for [{}]", token.getPrincipal());

        UsernamePasswordToken objToken = (UsernamePasswordToken) token;

        LoginLog loginLog = new LoginLog();
        loginLog.setUserid(token.getPrincipal().toString());

        String host = null;
        Subject user = SecurityUtils.getSubject();
        if (user != null && user.getSession() != null) {
            host = user.getSession().getHost();
        }
        if (!StringUtils.hasLength(host) || "0:0:0:0:0:0:0:1".equals(host)) {
            loginLog.setClientaddr(getClientIpAddress());
        } else {
            loginLog.setClientaddr(host);
        }


        loginLog.setAttemptdate(LocalDateTime.now());
        loginLog.setAttemptresult("登录成功");

        Sysuser sysuser = null;
        try {
            sysuser = sysuserService.findByUserid(token.getPrincipal().toString());
            LoginUserUtil.setUser(sysuser);
            loginLogService.save(loginLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(AuthenticationToken token, AuthenticationException ae) {
        log.info("Login failure for [{}]", token.getPrincipal());
        // LoginUserUtil.setUser(null);
    }

    @Override
    public void onLogout(PrincipalCollection principals) {
        log.info("Logout: [{}]", principals.getPrimaryPrincipal());

        String host = null;
        Subject user = SecurityUtils.getSubject();
        if (user != null && user.getPrincipal() != null) {
            host = user.getSession().getHost();
        }

        LoginLog loginLog = new LoginLog();
        loginLog.setUserid(principals.getPrimaryPrincipal().toString());
        loginLog.setClientaddr(host);
        if ("0:0:0:0:0:0:0:1".equals(loginLog.getClientaddr())) {
            loginLog.setClientaddr(getClientIpAddress());
        }
        loginLog.setAttemptdate(LocalDateTime.now());
        loginLog.setAttemptresult("注销成功");

        loginLogService.save(loginLog);
        LoginUserUtil.setUser(null);
    }

    private String getClientIpAddress() {
        String ip = null;
        Enumeration e;
        try {
            e = NetworkInterface.getNetworkInterfaces();

            int ctr = 0;
            while (e.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface) e.nextElement();
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration ee = iface.getInetAddresses();
                while (ee.hasMoreElements() && ctr < 3) {
                    ctr++;
                    if (ctr == 3)
                        break;
                    InetAddress i = (InetAddress) ee.nextElement();
                    if (ctr == 2) {
                        ip = i.getHostAddress();
                    }
                    System.out.println(i.getHostAddress());

                }
            }
        } catch (SocketException e1) {
            e1.printStackTrace();
            log.error(e1.toString());
        }
        return ip;
    }

}
