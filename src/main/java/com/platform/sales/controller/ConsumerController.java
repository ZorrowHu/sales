package com.platform.sales.controller;

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
     * 跳转到登录
     * @return
     */
    @GetMapping("/login")
    public String loginPage(){
        return "consumer/login";
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
                        HttpSession session,
                        RedirectAttributes redirectAttributes){

        Users user = usersService.userLogin(userName, password);    // 根据传过来的账户密码查询相应用户
        if (user != null && user.getUserRole().equals("消费者")){
            user.setPassword("");   // 将密码设空以免泄露
            session.setAttribute("user", user);
            return "redirect:/consumer/index";
        }
        // 默认为登陆错误
        redirectAttributes.addFlashAttribute("message", "用户名或密码错误，请重新输入！");
        return "redirect:/consumer/login";
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
     * 注册方法
     * @param userName  表单提交过来的用户信息
     * @param password  表单提交过来的用户密码
     * @param redirectAttributes    用于重载页面
     * @return
     */
    @PostMapping("/register")
    public String register(@RequestParam String userName,
                           @RequestParam String password,
                           RedirectAttributes redirectAttributes) {

        Users user = usersService.findByName(userName);

        if (userName == "" || password == ""){  // 当用户输入空白的信息
            redirectAttributes.addFlashAttribute("message", "请不要输入空白信息，用户名密码均为必填");
            return "redirect:/consumer/register";
        }else if (user != null){  // 当用户名已被占用，就重载到注册页并显示错误信息
            user.setPassword("");   // 将用户密码设空以免泄露信息
            redirectAttributes.addFlashAttribute("message", "该用户名已被占用，请输入其他用户名！");
            return "redirect:/consemer/register";
        }else{                 // 当用户名可用
            Users userInfo = new Users();
            userInfo.setUserName(userName);
            userInfo.setPassword(password);
            userInfo.setUserRole("消费者");
            usersService.addUsers(userInfo);    // 保存该用户
            redirectAttributes.addFlashAttribute("message", "");
            return "redirect:/consumer/login";   // 重载到登录页
        }
    }

}
