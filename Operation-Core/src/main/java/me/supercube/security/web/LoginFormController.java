package me.supercube.security.web;

import me.supercube.common.model.Message;
import me.supercube.common.model.Message4Entity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by chenping on 16/8/30.
 */
//@RestController
//@RequestMapping("/login")
public class LoginFormController {

    /**
     * 用户登陆页
     * */
    @RequestMapping(method = RequestMethod.GET)
    public Message showLoginForm(Model model) {
        //return "loginForm";
        return new Message(false,"未验证","2");
    }

}
