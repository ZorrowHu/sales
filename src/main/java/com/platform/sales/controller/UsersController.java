package com.platform.sales.controller;

import antlr.StringUtils;
import com.platform.sales.entity.Users;
import com.platform.sales.surface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    /**
     * 转到登录界面
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return "users/login";
    }

    /**
     * 登录方法
     * @param userName  页面表单提交的用户名
     * @param password  页面表单提交的密码
     * @param session   用来保存用户信息
     * @param redirectAttributes    用于重载页面
     * @return
     */
    @PostMapping("/login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        @RequestParam String userRole,
                        HttpSession session,
                        RedirectAttributes redirectAttributes){

        Users user = usersService.userLogin(userName, password, userRole);    // 根据传过来的账户密码查询相应用户
        if (user != null){
            user.setPassword("");   // 将密码设空以免泄露
            session.setAttribute("user", user);
            // 下列判断根据登陆者的身份信息，跳转到不同的页面
            switch (userRole){
                case "管理员":
                    return "redirect:/administrator/index";
                case "品牌商":
                    return "redirect:/brand/brandinfo";
                case "借卖方":
                    return "redirect:/Stores/storeindex";
            }
        }
        // 默认为登陆错误
        redirectAttributes.addFlashAttribute("message", "用户名或密码错误，请重新输入！");
        return "redirect:/user/login";
    }

    /**
     * 跳转到注册界面
     * @return
     */
    @GetMapping("/register")
    public String registerPage(){
        return "users/register";
    }


    /**
     * 注册方法
     * @param userName  表单提交过来的用户信息
     * @param password  表单提交过来的用户密码
     * @param userRole  表单提交过来的用户角色类型
     * @param redirectAttributes    用于重载页面
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestParam String userName,
                           @RequestParam String password,
                           @RequestParam String userRole,
                           RedirectAttributes redirectAttributes) {

        Users user = usersService.userRegister(userName, userRole);

        if (userName == "" || password == ""){  // 当用户输入空白的信息
            redirectAttributes.addFlashAttribute("message", "请不要输入空白信息，用户名密码均为必填");
            return "redirect:/user/register";
        }else if (user != null){  // 当用户名已被占用，就重载到注册页并显示错误信息
            user.setPassword("");   // 将用户密码设空以免泄露信息
            redirectAttributes.addFlashAttribute("message", "该用户名已被占用，请输入其他用户名！");
            return "redirect:/user/register";
        }else{                 // 当用户名可用
            Users userInfo = new Users();
            userInfo.setUserName(userName);
            userInfo.setPassword(password);
            userInfo.setUserRole(userRole);
            usersService.addUsers(userInfo);    // 保存该用户
            redirectAttributes.addFlashAttribute("message", "");
            return "redirect:/user/login";   // 重载到登录页
        }
    }


}
