package me.supercube.system.app.user.service;

import me.supercube.common.model.Message;
import me.supercube.common.model.Message4Collection;
import me.supercube.common.model.Message4Entity;
import me.supercube.system.app.user.model.Sysuser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 系统用户业务操作接口
 * <p>
 * Created by chenping on 16/8/25.
 */
public interface SysuserService {

    /**
     * 获取用户列表
     *
     * @param condition 查询条件
     * @param pageable  分页对象
     * @return
     */
    Page<Sysuser> findAll(Map<String, String> condition, Pageable pageable);

    /**
     * 获取指定ID的用户对象
     *
     * @param id 用户数据ID
     * @return {@link Sysuser} 用户对象
     */
    Sysuser findOne(long id);

    /**
     * 获取指定ID的用户对象
     *
     * @param userid 用户ID
     * @return
     */
    Sysuser findByUserid(String userid) throws Exception;


    boolean userIdExists(String userid);


    /**
     * 创建用户
     *
     * @param entity 用户对象
     * @return
     */
    Sysuser create(Sysuser entity) throws Exception;

    /***
     * 更新用户信息
     * @param entity 用户对象
     */
    Sysuser save(Sysuser entity) throws Exception;

    /**
     * 更新用户密码
     */
    Sysuser changePassword(Sysuser entity) throws Exception;

    /**
     * 删除用户
     *
     * @param id given user's id
     */
    Message deleteOne(long id);

    /**
     * delete users
     *
     * @param sysuserList given user list
     * @return
     */
    Message4Collection deleteByList(List<Sysuser> sysuserList);

    /**
     * 根据用例类型查询专家列表
     * @param type
     * @return
     */
    List<Sysuser> findByType(String type) throws Exception;

    Sysuser findByUserId(String userId) throws Exception;


    /**
     * 重置密码为123456
     * @param userid
     * @throws Exception
     */
    Sysuser revertPassword(String userid,String revertPwd) throws Exception;

    /**
     * 修改用户头像
     * @param userId
     * @param imageUrl
     * @return
     * @throws Exception
     */
    Sysuser changeUserImage(String userId,String imageUrl) throws Exception;


}
