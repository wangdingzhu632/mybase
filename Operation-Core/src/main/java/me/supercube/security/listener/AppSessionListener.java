package me.supercube.security.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * Created by chenping on 16/8/31.
 */
public class AppSessionListener implements SessionListener {

    @Override
    public void onStart(Session session) {
        System.out.println("会话创建：" + session.getId());
    }

    @Override
    public void onStop(Session session) {
        System.out.println("会话过期：" + session.getId());
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("会话停止：" + session.getId());
    }
}
