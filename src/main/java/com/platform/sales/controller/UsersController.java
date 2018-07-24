package com.platform.sales.controller;

import antlr.StringUtils;
import com.platform.sales.entity.Users;
import com.platform.sales.surface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("user")
public class UsersController {

    @Autowired
    private UsersService usersService;  // 用于用户相关的数据操作
    private Model model;

    /**
     * 转到登录界面
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return "users/login";
    }

    /**
     * 跳转到注册界面
     * @return
     */
    @GetMapping("/register")
    public String registerPage(){
        return "users/register";
    }

    @GetMapping("/reregister")
    public String reregisterPage(){
        return "users/reregister";
    }

    /**
     * 注册即添加新账户
     * @param user
     * @return
     */
    @PostMapping("/register")
    public String register(Users user){

        if (usersService.findByNameAndRole(user.getUser_name(), user.getUser_role()) != null){  // 当同类用户同名的时候，即该用户名不可用
            return this.reregisterPage();
        }else{  // 当该用户名可用
            usersService.addUsers(user);
            return "users/login";
        }

    }

}
