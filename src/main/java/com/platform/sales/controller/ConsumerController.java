package com.platform.sales.controller;

import com.platform.sales.entity.Users;
import com.platform.sales.repository.UsersRepository;
import com.platform.sales.surface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private UsersService usersService;

    /**
     * 跳转到主页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "consumer/index";
    }

    /**
     * 跳转到登录页
     * @return
     */
    @GetMapping("/login")
    public String login(){
        return  "consumer/login";
    }

    /**
     * 跳转到注册页
     * @return
     */
    @GetMapping("/register")
    public String registerPage(){
        return "consumer/register";
    }

    /**
     * 注册账户并跳转回登录页
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(Users user){
        user.setUserRole("消费者");   // 消费者的注册没有角色类型可选，所以指定为消费者
        usersService.addUsers(user);
        return "consumer/login";
    }

}
