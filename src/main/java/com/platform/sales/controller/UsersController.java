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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


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
     * 跳转到重新登录页面
     * @return
     */
//    @GetMapping("/relogin")
//    public String reloginPage(){
//        return "users/relogin";
//    }

    @PostMapping("/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes){

        Users user = usersService.userLogin(userName, password);    // 根据传过来的账户密码查询相应用户

        if (user != null && user.getUserRole() != "消费者"){
            user.setPassword("");   // 将密码设空以免泄露
            session.setAttribute("user", user);
            // 下列判断根据登陆者的身份信息，跳转到不同的页面
            switch (user.getUserRole()){
                case "管理员":
                    return "administrator/index";
                case "品牌商":
                    return "brand/index";
                case "借卖方":
                    return "Stores/index";
            }
        }
        // 默认为登陆错误
        redirectAttributes.addFlashAttribute("message", "用户名或密码错误，请重新输入！");
        return "redirect:/users/login";
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
        if (usersService.findByNameAndRole(user.getUserName(), user.getUserRole()) != null){  // 当同类用户同名的时候，即该用户名不可用
            return this.reregisterPage();
        }else{  // 当该用户名可用
            usersService.addUsers(user);
            return "users/login";
        }
    }

}
