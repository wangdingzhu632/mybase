package me.supercube.system.app.site.service;

import me.supercube.system.app.site.model.Sysinfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Map;
/**
 * 系统信息接口
 * Created by wangdz on 2016/9/21.
 */
public interface SysinfoService {
    /***
     * 保存项目信息
     * @param sysinfo 位置定义
     * @return
     */
    Sysinfo save(Sysinfo sysinfo)throws Exception;
    /***
     * 查找一条项目信息
     * @param orgid
     * @return
     */
    Sysinfo findOne(String orgid);
    /***
     *项目信息列表
     * @param pageable
     * @param condition 查询条件
     * @return
     */
    Page<Sysinfo> findAll(Map<String,String> condition, Pageable pageable);

}
