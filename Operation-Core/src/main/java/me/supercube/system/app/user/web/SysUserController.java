package me.supercube.system.app.user.web;

import com.google.common.io.ByteStreams;
import io.swagger.annotations.ApiOperation;
import me.supercube.common.model.Message4Collection;
import me.supercube.common.model.Message4Entity;
import me.supercube.common.model.Message4Page;
import me.supercube.common.util.ValidUtil;
import me.supercube.common.model.Message;
import me.supercube.system.app.user.model.Sysuser;
import me.supercube.system.app.user.service.SysuserService;
import me.supercube.security.util.PasswordHelper;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 系统用户管理
 * <p>
 * /users  GET
 * /users  POST
 * /users/:username  DELETE
 *
 * @author CHENPING
 */
@RestController
@RequestMapping("/api/sysuser")
//@CrossOrigin(origins="*", maxAge=3600)
public class SysUserController {

    private static final Logger logger = LoggerFactory.getLogger(SysUserController.class);


    @Autowired
    private SysuserService sysuserService;


    /**
     * 获取人员数据
     */
    @ApiOperation(value="获取系统用户列表", notes="")
    @RequestMapping(method = RequestMethod.GET)
    public Message4Page<Sysuser> getUsers(
            @RequestParam(value = "siteid", required = false,defaultValue="") String siteid,
            @RequestParam(value = "type", required = false,defaultValue="") String type,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "rows", required = false, defaultValue = "15") Integer rows,
            HttpServletRequest request) {


        String basePath = "http://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        Pageable pageable = new PageRequest(page - 1, rows);
        Map<String, String> condition = new HashMap<String, String>();

        if (StringUtils.hasLength(siteid)) {
            condition.put("siteid",siteid);
        }

        if (StringUtils.hasLength(type)) {
            condition.put("type",type);
        }

        if (request.getParameter("userid") != null) {
            condition.put("userid", request.getParameter("userid"));
        }

        if (request.getParameter("status") != null) {
            condition.put("status", request.getParameter("status"));
        }

        if (request.getParameter("keyword") != null) {
            condition.put("keyword", request.getParameter("keyword"));
        }

        Message4Page<Sysuser> result = null;
        Page<Sysuser> pageing = null;
        try {
            pageing = sysuserService.findAll(condition, pageable);
            //组装头像imgUrl路径
            List<Sysuser> sysuserList = pageing.getContent();
            for(Sysuser sysuser : sysuserList){
                sysuser.setImgUrl(basePath + sysuser.getImgUrl());
            }
            result = new Message4Page<Sysuser>(Message.success(), pageing);
        } catch (Exception e) {
            result = new Message4Page<Sysuser>(Message.fail(e.toString()), pageing);
        }
        return result;
    }

    /**
     * 获取指定人员的信息
     */
    @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
    public Message4Entity<Sysuser> getUserById(@PathVariable String userid,HttpServletRequest request) {
        if(!StringUtils.hasLength(userid)) {
            return new Message4Entity<Sysuser>(Message.fail("请输入用户ID"),null);
        }
        String basePath = "http://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        Message4Entity<Sysuser> message4Entity = null;
        try {
            Sysuser users = sysuserService.findByUserid(userid);
            if(users==null) {
                message4Entity = new Message4Entity<Sysuser>(Message.fail("用户不存在"),null);
            } else {
                users.setImgUrl(basePath + users.getImgUrl());
                message4Entity = new Message4Entity<Sysuser>(Message.success(),users);
            }
        } catch (Exception e) {
            logger.error("获取指定人员的信息失败",e);
            message4Entity = new Message4Entity<Sysuser>(Message.fail(e.toString()),null);
        }
        return message4Entity;
    }

    /**
     * 保存用户信息
     *
     * @param entity 用户信息
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Message4Entity<Sysuser> createUser(@RequestBody @Valid Sysuser entity, BindingResult result, HttpServletRequest request) {
        Message4Entity<Sysuser> message = null;
        if (result.hasErrors()) {
            message = new Message4Entity<Sysuser>(Message.fail(ValidUtil.getErrorMessage(result)), entity);
        } else {
            try {
                entity = sysuserService.save(entity);
                message = new Message4Entity<Sysuser>(Message.success(), entity);
            } catch (Exception e) {
                e.printStackTrace();
                message = new Message4Entity<Sysuser>(Message.fail(e.toString()), entity);
            }
        }
        return message;
    }


    /**
     * 保存用户信息
     *
     * @param entity 用户信息
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public Message4Entity<Sysuser> updateUser(@RequestBody @Valid Sysuser entity, BindingResult result, HttpServletRequest request) {
        Message4Entity<Sysuser> message = null;
        if (result.hasErrors()) {
            message = new Message4Entity<Sysuser>(Message.fail(ValidUtil.getErrorMessage(result)), entity);
        } else {
            try {
                entity = sysuserService.save(entity);
                message = new Message4Entity<Sysuser>(Message.success(), entity);
            } catch (Exception e) {
                message = new Message4Entity<Sysuser>(Message.fail(e.toString()), entity);
            }


        }
        return message;
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Message delete(@PathVariable("id") Long id) {
        try {
            sysuserService.deleteOne(id);
            return Message.success();
        } catch (Exception e) {
            logger.error("删除用户", e);
            return Message.fail(e.toString());
        }
    }


    /**
     * 检查用户ID是否已经存在
     *
     * @param userid 用户ID
     * @return true or false
     */
    @RequestMapping(value = "/check/{userid}", method = RequestMethod.GET)
    public Message checkUserid(@PathVariable String userid) {
        try {
            Message message = new Message();
            boolean isExist = sysuserService.userIdExists(userid);
            message.setSuccess(isExist);
            return message;
        } catch (Exception e) {
            logger.error("检查用户ID是否已经存在", e);
            return Message.fail(e.toString());
        }
    }

    @RequestMapping(value = "/manage/changepwd", method = RequestMethod.POST)
    public Message handleAdminChangePassword(@RequestBody Sysuser user) {
        if (!StringUtils.hasLength(user.getPassword())) {
            return Message.fail("请输入新的登陆密码");
        }
        try {
            user.setLoginid(user.getUserid());
            sysuserService.changePassword(user);
        } catch (Exception e) {
            return Message.fail(e.toString());
        }
        return Message.success();
    }

    /**
     * 更改用户登陆密码
     */
//    @RequestMapping(value = "/changepwd", method = RequestMethod.POST)
//    public Message changePassword(@RequestBody Sysuser user) {
//        if (!StringUtils.hasLength(user.getPassword())) {
//            return Message.fail("请输入当前登陆密码");
//        }
//        if (!StringUtils.hasLength(user.getPassword())) {
//            return Message.fail("请输入新的登陆密码");
//        }
//
//        Sysuser existUser = null;
//        try {
//            existUser = sysuserService.findByUserid(user.getUserid());
//        } catch (Exception e) {
//            return Message.fail(e.toString());
//        }
//        if (existUser == null) {
//            return Message.fail("用户名不存在");
//        } else {
//            boolean isCheck = PasswordHelper.newInstance().validationPassword(existUser, user.getPassword());
//            if (!isCheck) {
//                return Message.fail("当前登陆密码错误");
//            } else {
//                existUser.setPassword(user.getPassword());
//                existUser.setPasswordold(user.getPassword());
//                try {
//                    sysuserService.changePassword(existUser);
//                } catch (Exception e) {
//                    return Message.fail(e.toString());
//                }
//                return Message.success();
//            }
//        }
//
//    }

    /**
     * 校验当前登录密码（为提高系统安全性，有些操作前，需要校验当前用户密码）
     * @return
     */
    @RequestMapping(value = "/checkpwd", method = RequestMethod.POST)
    public Message checkPassword(@RequestBody Sysuser user){
        Sysuser existUser = null;
        boolean isCheck = false;
        try {
            existUser = sysuserService.findByUserid(user.getUserid());
        } catch (Exception e) {
            return Message.fail(e.toString());
        }
        if (existUser == null) {
            return Message.fail("用户名不存在");
        } else {
            isCheck = PasswordHelper.newInstance().validationPassword(existUser, user.getPassword());
        }
        if(isCheck){
            return Message.success("密码验证通过");
        }else{
            return Message.fail("当前密码输入不正确");
        }

    }


    /**
     * 重置密码
     * @param
     * @return
     */
    @RequestMapping(value = "/revertpwd", method = RequestMethod.GET)
    public Message revertPassword(@RequestParam(value = "userid", required = false,defaultValue="") String userid){
        if(!StringUtils.hasLength(userid)){
            return  Message.fail("用户id不能为空!");
        }
        String revertPwd = "123456";
        try {
           Sysuser sysuser = sysuserService.revertPassword(userid,revertPwd);
            if(null == sysuser){
                return  Message.fail("用户重置密码失败!");
            }else{
                return  Message.success("用户重置密码成功!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  Message.fail("用户重置密码异常："+e);
        }
    }

    /**
     * 查找专家列表
     * @return
     */
    @RequestMapping(value = "/specialist", method = RequestMethod.GET)
    public Message4Collection getSpecialist(HttpServletRequest request){
        List<Sysuser> speciaList = new ArrayList<Sysuser>();
        String basePath = "http://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath();
        try {
            // SYSTEM=系统管理员类型  SPECIALIST=专家   FARMER=农户
            String type = "SPECIALIST";
            speciaList = sysuserService.findByType(type);
            for(Sysuser sysuser : speciaList){
                sysuser.setImgUrl(basePath + sysuser.getImgUrl());
            }
            if(speciaList.isEmpty()){
                return new Message4Collection(Message.fail("专家列表为空"),null);
            }else{
                return new Message4Collection(Message.success(),speciaList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Message4Collection(Message.fail("查询专家列表异常"),null);
        }
    }

    /**
     * 上传用户头像
     * @return
     */
    @RequestMapping(value = "/upload/image/{userid}", method = RequestMethod.POST)
    public Message uploadUserImage(HttpServletRequest request, HttpServletResponse response,@PathVariable String userid) throws ServletException, IOException,FileUploadException {
        if(!StringUtils.hasLength(userid)){
            return Message.fail("用户id不能为空!");
        }
        Sysuser sysuser = null;
        try {
            sysuser = sysuserService.findByUserid(userid);
            if(null == sysuser){
                return Message.fail("用户【"+userid+"】查询为空!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Message.fail("查询用户【"+userid+"】异常!");
        }
        ServletContext application = request.getSession().getServletContext();
        String savePath = application.getRealPath("/") + "upload/";
        // 文件保存目录URL
        String saveUrl = request.getContextPath() + "/upload/";
        // 定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");
        // 最大文件大小 10兆
        long maxSize = 10485760;
        response.setContentType("text/html; charset=UTF-8");
        if (!ServletFileUpload.isMultipartContent(request)) {
            return Message.fail("请选择文件。");
        }
        // 检查目录
        File uploadDir = new File(savePath);
        if (!uploadDir.isDirectory()) {
            return Message.fail("上传目录不存在。");
        }
        // 检查目录写权限
        if (!uploadDir.canWrite()) {
            return Message.fail("上传目录没有写权限。");
        }
        //文件后缀
        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "image";
        }
        if (!extMap.containsKey(dirName)) {
            return Message.fail("目录名不正确。");
        }
        // 创建文件夹
        savePath += dirName + "/";
        saveUrl += dirName + "/";
        File saveDirFile = new File(savePath);
        if (!saveDirFile.exists()) {
            saveDirFile.mkdirs();
        }
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        String ymd = sdf.format(new Date());
//        savePath += ymd + "/";
//        saveUrl += ymd + "/";
        File dirFile = new File(savePath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        //利用Spring的MultipartHttpServletRequest上传文件
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Iterator item = multipartRequest.getFileNames();
        while (item.hasNext()) {
            String fileName = (String) item.next();
            MultipartFile file = multipartRequest.getFile(fileName);
            // 检查文件大小
            if (file.getSize() > maxSize) {
                return Message.fail("上传文件大小超过限制。");
            }
            // 检查扩展名
            String fileExt = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).toLowerCase();
            if (!Arrays. asList(extMap.get(dirName).split(",")).contains(fileExt)) {
                return Message.fail("上传文件扩展名是不允许的扩展名。\n只允许"
                        + extMap.get(dirName) + "格式。");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
            try {
                File uploadedFile = new File(savePath, newFileName);
                ByteStreams.copy(file.getInputStream(), new FileOutputStream(uploadedFile));
            } catch (Exception e) {
                return Message.fail("上传文件失败。");
            }
            //上传图片保存路径
            String imageUrl = "/upload/image/"+newFileName;
            //String iconPath ="http://"+ request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/weathercn/";
            try {
                //更新用户头像url路径
                sysuserService.changeUserImage(userid,imageUrl);
            } catch (Exception e) {
                e.printStackTrace();
                return Message.fail("更新用户头像异常!");
            }
            return Message.success();
        }
        return null;
    }

}
