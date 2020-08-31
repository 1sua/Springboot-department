package com.ls.controller;

import com.ls.pojo.User;
import com.ls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class loginController {

    @Autowired
    private UserService service;

    @RequestMapping("/user/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        User user = service.UserLogin(username, password);

        if (user != null) {//登录成功
            //用户信息存到session中
            session.setAttribute("loginUser", username);
            //防止表单重复提交，使用重定向
            return "redirect:/main.html";
        } else {//登录失败
            model.addAttribute("msg", "用户名或密码错误");
            return "index";
        }

    }

    @RequestMapping("/user/logout")
    public String logout(HttpSession session) {
        session.invalidate();//销毁session
        return "index";
    }

}
