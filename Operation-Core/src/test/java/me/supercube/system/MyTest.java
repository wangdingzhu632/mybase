package me.supercube.system;

import me.supercube.system.app.user.model.Sysuser;
import me.supercube.system.app.user.service.SysuserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 用户测试类
 */
@ContextConfiguration(locations = {"classpath:spring/business-config.xml",
        "classpath:spring/quartz-config.xml",
        "classpath:spring/tools-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MyTest {
    @Autowired
    private SysuserService sysuserService;
    private final static Log logger = LogFactory.getLog(MyTest.class);
    /**
     * 新增
     * @throws Exception
     */
    @Test
    public void saveTest() throws Exception {
        Sysuser sysuser = new Sysuser();
        sysuser.setUserid("ios");
        sysuser.setLoginid("ios");
        sysuser.setPassword("ios");
        sysuser.setTruename("苹果");
        sysuser.setType("SYSTEM");
        sysuser.setStatus("1");
        sysuser.setFailedlogins(0);
        sysuser.setSalt("1");
        sysuser.setSysuser(true);
        sysuserService.save(sysuser);

        logger.info("-------Save OK-----");
    }

    /**
     * 查询所有
     *
     * @throws Exception
     */
    @Test
    public void findAllTest() throws Exception {
        Pageable pageable = new PageRequest(0, 15);
        Map<String,String> condition = new HashMap<String, String>();
        Page<Sysuser> data = sysuserService.findAll(condition, pageable);
        List<Sysuser> list = data.getContent();
        for (Sysuser c : list) {
            System.out.println(c.getUserid());
        }
        logger.info("-------FindAll OK-----");
    }

    /**
     * 查询一条
     *
     * @throws Exception
     */
    @Test
    public void findOneTest() throws Exception {
        Sysuser sysuser = sysuserService.findBySysuserid("b9a008abeddb4771af3ab585dc093ca5");
        System.out.println(sysuser.getTruename());
        logger.info("-------findOne OK-----");
    }


    @Test
    public void changePassword() throws Exception {
        Sysuser sysuser = new Sysuser();
        sysuser.setLoginid("test");
        sysuser.setUserid("test");
        sysuser.setPassword("111111");
        sysuserService.changePassword(sysuser);

    }


}
