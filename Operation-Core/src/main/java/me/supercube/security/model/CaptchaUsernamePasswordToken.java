package me.supercube.security.model;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * Created by chenping on 16/8/30.
 */
public class CaptchaUsernamePasswordToken extends UsernamePasswordToken {

    private String captcha;
    private String type;

    public CaptchaUsernamePasswordToken(String username, String password,
                                        boolean rememberMe, String type) {
        super(username, password, rememberMe);
        this.type = type;
    }

    public CaptchaUsernamePasswordToken(String username, char[] password,
                                        boolean rememberMe, String host, String type) {
        super(username, password, rememberMe, host);
        this.type = type;
    }

    public CaptchaUsernamePasswordToken(String username, char[] password,
                                        boolean rememberMe, String host, String captcha, String type) {
        super(username, password, rememberMe, host);
        this.captcha = captcha;
        this.type = type;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
