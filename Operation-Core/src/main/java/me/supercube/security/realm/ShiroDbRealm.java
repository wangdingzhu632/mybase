package me.supercube.security.realm;


import me.supercube.security.model.CaptchaUsernamePasswordToken;
import me.supercube.security.model.Permission;
import me.supercube.security.model.Role;
import me.supercube.security.service.PermissionService;
import me.supercube.security.service.RoleService;
import me.supercube.system.app.user.model.Sysuser;
import me.supercube.system.app.user.service.SysuserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    private SysuserService sysuserService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    private List<String> defaultPermission = new ArrayList<String>();

    @Autowired
    public ShiroDbRealm(ApplicationContext ctx) {
        super();
        // 不能注入 因为获取bean依赖顺序问题造成可能拿不到某些bean报错
        // why？
        // 因为spring在查找findAutowireCandidates时对FactoryBean做了优化，即只获取Bean，但不会autowire属性，
        // 所以如果我们的bean在依赖它的bean之前初始化，那么就得不到ObjectType（永远是Repository）
        // 所以此处我们先getBean一下 就没有问题了
        //ctx.getBeansOfType(SimpleBaseRepositoryFactoryBean.class);
    }

    /**
     * 设置默认permission
     *
     * @param defaultPermissionString Permission 如果存在多个值，使用逗号","分割
     */
    public void setDefaultPermissionString(String defaultPermissionString) {
        String[] perms = StringUtils.split(defaultPermissionString, ",");
        CollectionUtils.addAll(defaultPermission, perms);
    }

    /**
     * 设置默认permission
     *
     * @param defaultPermission Permission
     */
    public void setDefaultPermission(List<String> defaultPermission) {
        this.defaultPermission = defaultPermission;
    }

    /**
     * 为当前登录的Subject授予角色和权限
     */
    @Override
    public AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {

        if (principals == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        // 获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()
        String loginid = (String) super.getAvailablePrincipal(principals);
        // 当前用户对象
        Sysuser currUser = null;
        try {
            currUser = sysuserService.findByUserid(loginid);
        } catch (Exception e) {
            throw new AuthorizationException(e);
        }
        if (currUser == null) {
            throw new AuthorizationException();
        }
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        try {
            //通过用户类型获取对应角色
            String userType = currUser.getType();
            List<String> roleList = roleService.findByUserType(userType);
            authorizationInfo.setRoles(new HashSet<String>(roleList));

//            //根据角色获取对应权限
//            List<String> permissionList = new ArrayList<String>();
//            for(String roleCode : roleList){
//                List<String> list = permissionService.findByRoleCode(roleCode);
//                permissionList.addAll(list);
//            }
//            authorizationInfo.setStringPermissions(new HashSet<String>(permissionList));

        } catch (Exception e) {
            e.printStackTrace();
        }

//		 SecurityUtils.getSubject().getSession().setAttribute("permissions", permissions);
        return authorizationInfo;
    }

    /**
     * 验证当前登录的Subject
     * 该方法的调用时机为AuthenticationController.processLoginForm()方法中执行Subject.login
     * ()时
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException {
        // 获取基于用户名和密码的令牌
        // 实际上这个authcToken是从AuthenticationController里面user.login(token)传过来的
        CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
        if (!StringUtils.hasLength(token.getUsername())) {
            throw new AccountException("用户名不能为空");
        }
        try {
            //从数据库中查询用户用信息
            //系统用户校验
            Sysuser currUser = sysuserService.findByUserid(token.getUsername());
            if (currUser == null) {
                throw new UnknownAccountException("用户名没找到");// 没找到帐号
            }
            if (currUser.getStatus().equals("INACTIVE")) {
                throw new DisabledAccountException("账号未审核");
            }
            SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                    currUser.getLoginid(), // 用户名
                    currUser.getPassword(), // 密码
                    ByteSource.Util.bytes(currUser.getSalt()),// salt=username+salt
                    getName() // realm name
            );

            //登录成功之后获取当前用户对应的权限
            //暂时没这个需求
            //this.findSysMenuByUserType(currUser);

            return authenticationInfo;
        } catch (Exception e) {
            throw new AuthenticationException(e);
        }
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    /**
     * 根据用户类型（角色）获取菜单，放到SERVLETCONTEXT中
     */
    public void findSysMenuByUserType(Sysuser currUser) {

        ServletRequest request = ((WebSubject) SecurityUtils.getSubject())
                .getServletRequest();
        HttpSession httpSession = ((HttpServletRequest) request).getSession();
        ServletContext servletContext = httpSession.getServletContext();// 获取servletContext实例
        try {
            String userType = currUser.getType();
            List<Permission> permissionList = permissionService.findPermissionByRoleCode(userType);
            servletContext.setAttribute("permission", permissionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
