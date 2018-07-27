package com.platform.sales.controller;

import com.platform.sales.entity.OrderInfo;
import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandInfoRepository;
import com.platform.sales.repository.BrandOrderRepository;
import com.platform.sales.repository.ShipAddrRepository;
import com.platform.sales.repository.TypeRepository;
import com.platform.sales.surface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("consumer")
public class ConsumerController {

    @Autowired
    private UsersService usersService;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    private BrandOrderRepository brandOrderRepository;

    /**
     * 跳转到主页
     * @return
     */
    @GetMapping("/index")
    public String index(Model model){
        List<Type> types = typeRepository.findAll();

        HashMap<String, Object> sum = new HashMap<String,  Object>();

        List<String> primaries = typeRepository.getPrimary();
        List<String> secondaries = typeRepository.getSecondary();
        List<String> tertiaries = typeRepository.getTertiary();

        HashMap<String, Object> primary = new HashMap<String,  Object>();
        for (String first : primaries){
            HashMap<String, Object> secondary = new HashMap<String, Object>();
            for (String second : secondaries){
                HashMap<String, Integer> tertiary = new HashMap<String, Integer>();
                for (String third : tertiaries){
                    for (Type type : types){
                        if (type.getContent3().equals(third) && type.getContent2().equals(second) && type.getContent1().equals(first))
                        {
                            tertiary.put(type.getContent3(), type.getTypeId());
                             secondary.put(type.getContent2(), tertiary);
                            primary.put(type.getContent1(), secondary);
                        }
                    }
                }
            }
        }

        model.addAttribute("primaries", primary);
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

        Users user = usersService.consumerLogin(userName, password);    // 根据传过来的账户密码查询相应用户
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
            redirectAttributes.addFlashAttribute("message", "请不要输入空白信息");
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


    /**
     * 跳转到历史订单首页并根据用户id查询其所有历史订单信息
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/myOrders/{id}")
    public String myOrdersPage(@PathVariable("id") Integer id, Model model){
        List<OrderInfo> orders = brandOrderRepository.findAllByConsumer_UserId(id);
        model.addAttribute("orders", orders);
        return "consumer/myOrders";
    }

    /**
     * 根据订单id进行申请退款操作
     * @param id
     * @return
     */
    @GetMapping("/refund/{id}")
    public String refund(@PathVariable("id") Integer id){
        OrderInfo order = brandOrderRepository.findById(id).get();
        order.setStatus("待退款");
        brandOrderRepository.save(order);
        return "redirect:/consumer/myOrders/"+order.getConsumer().getUserId();
    }

}
