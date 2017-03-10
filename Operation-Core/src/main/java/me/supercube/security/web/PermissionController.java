package me.supercube.security.web;

import me.supercube.common.model.Message;
import me.supercube.common.model.Message4Collection;
import me.supercube.common.model.Message4Page;
import me.supercube.security.model.Permission;
import me.supercube.security.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限控制
 * Created by wangdz on 2016/11/9.
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(method = RequestMethod.GET)
    public Message4Collection<Permission> pageCustomer(@RequestParam(value = "parent", required = false, defaultValue = "") String parent ,
                                                       @RequestParam(value = "userType", required = false, defaultValue = "") String userType ,HttpServletRequest request){
        List<Permission> filterList = new ArrayList<Permission>();
        try {
            if(!StringUtils.hasLength(parent)){
                return new Message4Collection<Permission>(Message.fail("父菜单不能为空!"),filterList);
            }
            if(!StringUtils.hasLength(userType)){
                return new Message4Collection<Permission>(Message.fail("用户类型不能为空!"),filterList);
            }

            //从系统缓存中取
//            HttpSession httpSession = ((HttpServletRequest) request).getSession();
//            ServletContext servletContext = httpSession.getServletContext();// 获取servletContext实例
//            List<Permission> permissionList =(List<Permission>)servletContext.getAttribute("permission");

            //根据用户类型及上级菜单获取对应的子菜单
            List<Permission> permissionList = permissionService.findPermissionByRoleCode(userType);
            //根据父菜单过滤出对应的子菜单
            for(Permission permission : permissionList){
                if(parent.equals(permission.getParent())){
                    filterList.add(permission);
                }
            }
            return new Message4Collection<Permission>(Message.success(),filterList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Message4Collection<Permission>(Message.success(),filterList);
        }
    }

}
