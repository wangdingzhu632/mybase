package me.supercube.security.util;

import me.supercube.common.util.SpringContextHolder;
import me.supercube.system.app.user.model.Sysuser;
import me.supercube.system.app.user.service.SysuserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class LoginUserUtil {

    public static ThreadLocal<Sysuser> thread = new ThreadLocal<Sysuser>();

    private static SysuserService userService = SpringContextHolder.getBean(SysuserService.class);

    /**
     * 获取登陆用户ID
     */
    public static Sysuser getUser() {
        Sysuser users = null;
        try {
            Subject subject = SecurityUtils.getSubject();
            if (subject != null && subject.getPrincipal() != null) {
                String userid = subject.getPrincipal().toString();
                if (userService != null) {
                    users = userService.findByUserid(userid);
                    thread.set(users);
                }
            }
        } catch (Exception e) {
            users = thread.get();
        }
        return users;
    }


    public static void setUser(Sysuser users) {
        thread.set(users);
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

}
