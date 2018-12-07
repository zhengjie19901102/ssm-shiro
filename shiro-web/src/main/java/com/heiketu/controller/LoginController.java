package com.heiketu.controller;

import com.heiketu.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    /**
     * 登陆URL
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 去登录
     */
    @RequestMapping("/toLogin")
    public String toLogin(User user){
        Subject subject;
        try {
            subject = SecurityUtils.getSubject();
            UsernamePasswordToken up = new UsernamePasswordToken(user.getUsername(),user.getPassword());
            up.setRememberMe(true);
            subject.login(up);
        } catch (AuthenticationException e) {
            return "error_account";
        }

        return "index";
    }

    @RequestMapping(value = "/test",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String files(){
        return "success";
    }

    @RequiresPermissions("abc")
    @RequestMapping(value = "/fileOks",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String filesOk(){
        return "success";
    }

}
