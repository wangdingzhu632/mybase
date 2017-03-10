package me.supercube.portal.common;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Created by chenping on 16/3/16.
 */
public class InitializeApplicationProcessor implements ApplicationListener<ContextRefreshedEvent> {

    public static Long Begin;  ;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //保持系统启动时间
        Begin = System.currentTimeMillis();
        //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
        System.out.println("初始化代码...");
    }

}
