package com.platform.sales.controller;

import com.platform.sales.entity.*;
import com.platform.sales.repository.*;

import com.platform.sales.entity.OrderInfo;
import com.platform.sales.entity.StoreGoods;
import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandOrderRepository;
import com.platform.sales.repository.StoregoodsRepository;
import com.platform.sales.repository.TypeRepository;
import com.platform.sales.surface.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private StoregoodsRepository storegoodsRepository;
    @Autowired
    private BrandAccountRepository brandAccountRepository;
    @Autowired
    private ShipAddrRepository shipAddrRepository;

    /**
     * 跳转到主页
     * @return
     */
    @GetMapping("/index")
    public String index(Model model, HttpSession session){
        List<Type> types = typeRepository.findAll();

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

        List<StoreGoods> storeGoods = storegoodsRepository.findAll();
        Users user = (Users)session.getAttribute("consumer");
        Float totalPrice = new Float(0);
        if (user != null){
            List<OrderInfo> orders = brandOrderRepository.findAllByConsumer_UserIdAndStatus(user.getUserId(), "待支付");
            for (OrderInfo each : orders){
                totalPrice += each.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("goods", storeGoods);
        model.addAttribute("primaries", primary);
        return "consumer/index";
    }

    @GetMapping("/search/{id}")
    public String search(@PathVariable("id") Integer typeId, Model model, HttpSession session){
        List<Type> types = typeRepository.findAll();

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
        Type type = typeRepository.findById(typeId).get();
        String keyword = type.getContent1() + " > " + type.getContent2() + " > " + type.getContent3() ;
        List<StoreGoods> storeGoods = storegoodsRepository.findAllByBrandReposTypeTypeId(typeId);

        Users user = (Users)session.getAttribute("consumer");
        Float totalPrice = new Float(0);
        if (user != null){
            List<OrderInfo> orders = brandOrderRepository.findAllByConsumer_UserIdAndStatus(user.getUserId(), "待支付");
            for (OrderInfo each : orders){
                totalPrice += each.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);


        model.addAttribute("goods", storeGoods);
        model.addAttribute("primaries", primary);
        model.addAttribute("keyword", keyword);

        return "consumer/search";
    }

    @PostMapping("/search")
    public String search(@RequestParam String keyword, Model model, HttpSession session) {
        List<Type> types = typeRepository.findAll();

        List<String> primaries = typeRepository.getPrimary();
        List<String> secondaries = typeRepository.getSecondary();
        List<String> tertiaries = typeRepository.getTertiary();

        HashMap<String, Object> primary = new HashMap<String, Object>();
        for (String first : primaries) {
            HashMap<String, Object> secondary = new HashMap<String, Object>();
            for (String second : secondaries) {
                HashMap<String, Integer> tertiary = new HashMap<String, Integer>();
                for (String third : tertiaries) {
                    for (Type type : types) {
                        if (type.getContent3().equals(third) && type.getContent2().equals(second) && type.getContent1().equals(first)) {
                            tertiary.put(type.getContent3(), type.getTypeId());
                            secondary.put(type.getContent2(), tertiary);
                            primary.put(type.getContent1(), secondary);
                        }
                    }
                }
            }
        }
        model.addAttribute("primaries", primary);
        List<StoreGoods> storeGoods = storegoodsRepository.getByGoodNameLike(keyword);

        Users user = (Users)session.getAttribute("consumer");
        Float totalPrice = new Float(0);
        if (user != null){
            List<OrderInfo> orders = brandOrderRepository.findAllByConsumer_UserIdAndStatus(user.getUserId(), "待支付");
            for (OrderInfo each : orders){
                totalPrice += each.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("goods", storeGoods);
        model.addAttribute("primaries", primary);
        model.addAttribute("keyword", keyword);

        return "consumer/search";
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
            session.setAttribute("consumer", user);
            return "redirect:/consumer/index";
        }
        // 默认为登陆错误
        redirectAttributes.addFlashAttribute("message", "用户名或密码错误，请重新输入！");
        return "redirect:/consumer/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.setAttribute("consumer", null);
        return "redirect:/consumer/index";
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
            return "redirect:/consumer/register";
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
     *购物车
     * @param model
     * @return
     */
    @GetMapping("/checkout")
    public String checkout(Model model,HttpSession session){
        Users consumer = (Users) session.getAttribute("consumer");
        ShipAddr addr = shipAddrRepository.findByUsers(consumer);
        ShipAddr newaddr = new ShipAddr();
        if(addr == null){
            newaddr.setUsers(consumer);
            shipAddrRepository.save(newaddr);
        }else
            newaddr = addr;
        List<OrderInfo> orders = brandOrderRepository.findAllByStatusAndAndConsumer("待支付",consumer);
        for(int i = 0; i < orders.size(); i++){
            orders.get(i).setShip(newaddr);
            brandOrderRepository.save(orders.get(i));
        }
        model.addAttribute("orders",orders);
        if(orders.size() == 0)
            model.addAttribute("message","空空如也！！！");

        Users user = (Users)session.getAttribute("consumer");
        Float totalPrice = new Float(0);
        if (user != null){
            List<OrderInfo> orders_price = brandOrderRepository.findAllByConsumer_UserIdAndStatus(user.getUserId(), "待支付");
            for (OrderInfo each : orders_price){
                totalPrice += each.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);


        return "/consumer/checkout";
    }

    @GetMapping("/pay")
    public String pay(HttpSession session,Model model){
        Users consumer = (Users) session.getAttribute("consumer");
        ShipAddr addr = shipAddrRepository.findByUsers(consumer);
        model.addAttribute("addr",addr);


        Users user = (Users)session.getAttribute("consumer");
        Float totalPrice = new Float(0);
        if (user != null){
            List<OrderInfo> orders_price = brandOrderRepository.findAllByConsumer_UserIdAndStatus(user.getUserId(), "待支付");
            for (OrderInfo each : orders_price){
                totalPrice += each.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        return "/consumer/address";
    }

    @GetMapping("/cancelby/{id}")
    public String cancelBy(@PathVariable("id") Integer id,HttpSession session, Model model){
        brandOrderRepository.deleteById(id);
        Users consumer = (Users) session.getAttribute("consumer");
        List<OrderInfo> orders = brandOrderRepository.findAllByStatusAndAndConsumer("待支付",consumer);
        if(orders.size() == 0)
            model.addAttribute("message","空空如也！！！");
        model.addAttribute("orders",orders);
        return "/consumer/checkout";
    }

    @GetMapping("/cancel")
    public String cancel(HttpSession session,Model model){
        Users consumer = (Users) session.getAttribute("consumer");
        List<OrderInfo> orders = brandOrderRepository.findAllByStatusAndAndConsumer("待支付",consumer);
        for(int i = 0; i < orders.size(); i++){
            brandOrderRepository.deleteById(orders.get(i).getOrderId());
        }
        model.addAttribute("message","空空如也！！！");
        return "/consumer/checkout";
    }

    @PostMapping("/address")
    public String address(HttpSession session,Model model,ShipAddr addr){
        Users consumer = (Users) session.getAttribute("consumer");
        List<OrderInfo> orders = brandOrderRepository.findAllByStatusAndAndConsumer("待支付",consumer);
        Float total = new Float(0);
        Date time = new Date();
        for(int i = 0; i < orders.size(); i++){
            orders.get(i).setStatus("已支付");
            orders.get(i).setPayTime(time);
            brandOrderRepository.save(orders.get(i));
            total += orders.get(i).getTotalPrice();
        }
        ShipAddr getaddr = shipAddrRepository.findByUsers(consumer);
        addr.setShipId(getaddr.getShipId());
        shipAddrRepository.save(addr);
        model.addAttribute("message","已支付"+total+"元！");

        Users user = (Users)session.getAttribute("consumer");
        Float totalPrice = new Float(0);
        if (user != null){
            List<OrderInfo> orders_price = brandOrderRepository.findAllByConsumer_UserIdAndStatus(user.getUserId(), "待支付");
            for (OrderInfo each : orders_price){
                totalPrice += each.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);

        return "/consumer/checkout";
    }

    /**
     * 跳转到历史订单首页并根据用户id查询其所有历史订单信息
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/myOrders/{id}")
    public String myOrdersPage(@PathVariable("id") Integer id, Model model,HttpSession session){
        List<OrderInfo> orders = brandOrderRepository.findAllByConsumer_UserIdOrderByPayTime(id);
        model.addAttribute("orders", orders);
        List<Type> types = typeRepository.findAll();

        List<String> primaries = typeRepository.getPrimary();
        List<String> secondaries = typeRepository.getSecondary();
        List<String> tertiaries = typeRepository.getTertiary();

        HashMap<String, Object> primary = new HashMap<String, Object>();
        for (String first : primaries) {
            HashMap<String, Object> secondary = new HashMap<String, Object>();
            for (String second : secondaries) {
                HashMap<String, Integer> tertiary = new HashMap<String, Integer>();
                for (String third : tertiaries) {
                    for (Type type : types) {
                        if (type.getContent3().equals(third) && type.getContent2().equals(second) && type.getContent1().equals(first)) {
                            tertiary.put(type.getContent3(), type.getTypeId());
                            secondary.put(type.getContent2(), tertiary);
                            primary.put(type.getContent1(), secondary);
                        }
                    }
                }
            }
        }
        model.addAttribute("primaries", primary);
        Users user = (Users)session.getAttribute("consumer");
        Float totalPrice = new Float(0);
        if (user != null){
            List<OrderInfo> orders_price = brandOrderRepository.findAllByConsumer_UserIdAndStatus(user.getUserId(), "待支付");
            for (OrderInfo each : orders_price){
                totalPrice += each.getTotalPrice();
            }
        }
        model.addAttribute("totalPrice", totalPrice);

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
