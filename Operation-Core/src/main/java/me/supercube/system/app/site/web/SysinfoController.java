package me.supercube.system.app.site.web;

import me.supercube.common.model.Message;
import me.supercube.common.model.Message4Entity;
import me.supercube.common.model.Message4Page;
import me.supercube.common.util.ValidUtil;
import me.supercube.system.app.site.model.Sysinfo;
import me.supercube.system.app.site.service.SysinfoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统信息控制层逻辑
 * Created by wangdz on 2016/9/21.
 */
@RestController
@RequestMapping("/sysinfo")
public class SysinfoController {
    @Autowired
    private SysinfoService sysinfoService;
    private final static Log logger = LogFactory.getLog(SysinfoController.class);
    /***
     *查询系统信息列表
     * @param page 页数
     * @param rows
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Message4Page<Sysinfo> pageLocations(
            @RequestParam(value="page",required=false,defaultValue="1")Integer page,
            @RequestParam(value="rows",required=false,defaultValue="15") Integer rows,
            HttpServletRequest request){
        Sort sort=new Sort(Sort.Direction.DESC,"locationsid");
        Pageable pageable=new PageRequest(page-1, rows,sort);
        Map<String,String> condition=new HashMap<String,String>();
        // condition.put("",);
        Page<Sysinfo> pageing = null;
        Message4Page<Sysinfo> result = null;
        try {
            pageing = sysinfoService.findAll(condition,pageable);
            result= new Message4Page<Sysinfo>(Message.success(),pageing);
        } catch (Exception e) {
            e.printStackTrace();
            result= new Message4Page<Sysinfo>(Message.fail(e.toString()),pageing);
        }
        return result;
    }
    /***
     * 保存系统信息
     * @param sysinfo
     * @param result
     * @return
     */
    @RequestMapping(method=RequestMethod.POST)
    public Message4Entity<Sysinfo> save(@Valid @RequestBody Sysinfo sysinfo, BindingResult result){
        Message4Entity<Sysinfo> message = null;
        if(result.hasErrors()){
            message = new Message4Entity<Sysinfo>(Message.fail(ValidUtil.getErrorMessage(result)), sysinfo);
        }else{
            try{
                sysinfo = sysinfoService.save(sysinfo);
                message = new Message4Entity<Sysinfo>(Message.success(),sysinfo);
            }catch(Exception e){
                logger.error(e);
                e.printStackTrace();
                message = new Message4Entity<Sysinfo>(Message.fail(e.toString()), sysinfo);
            }
        }
        return message;
    }

    /**
     *获取系统信息
     * @param orgid
     * @return
     */
    @RequestMapping(value = "/{orgid}", method = RequestMethod.GET)
    public Message4Entity<Sysinfo> findOne(@PathVariable String orgid) {
        Message4Entity<Sysinfo> message = null;
        Sysinfo sysinfo = null;
        try {
            sysinfo = sysinfoService.findOne(orgid);
            if (null != sysinfo){
                message=new Message4Entity<Sysinfo>(Message.success(),sysinfo);
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
            message=new Message4Entity<Sysinfo>(Message.fail(e.toString()), sysinfo);
        }
        return message;
    }

}
