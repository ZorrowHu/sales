package com.platform.sales.controller;
//备份成功
import com.platform.sales.entity.*;
import com.platform.sales.surface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Controller
@RequestMapping("seller")
public class SellerinfoController {
    @Autowired
    SellerinfoService sellerinfoService;
    //钱包账户服务
    @Autowired
    BrandAccountService accountService;
    //流水服务
    @Autowired
    BrandRecordService recordService ;
    //商店服务
    @Autowired
    StoresService storesService;
    @Autowired
    BrandOrderService orderService;
    //品牌商商品服务
    @Autowired
    BrandReposService reposService;
    //需要将借卖方注册时的user_id添加到页面属性中作为此方法的参数
    @GetMapping("/getInfo/{seller_id}")
    public String getInfo(@PathVariable("seller_id")Integer id, Model model){
        List<SellerInfo> sellers = sellerinfoService.findById(id);//根据表数据中的外键查询结果
        SellerInfo seller_info;
        if(sellers.size()==0){//查询结果为null，说明借卖方的信息还未初始化，所有像都为空
            seller_info = new SellerInfo();
            model.addAttribute("id",-1);//将id的值赋予-1写到前端属性中，方便判断接下来提交的修改操作是添加新项还是修改已有项
            seller_info.setMail("");
            seller_info.setUserName("");
            seller_info.setPhone("");
            Users user = new Users();
            user.setUserId(id);
            seller_info.setUser(user);
        }else {
            seller_info = sellers.get(0);
            model.addAttribute("id",seller_info.getSellerId());//查询到结果则将结果填到页面中去
        }
        model.addAttribute("sellerinfo",seller_info);
        return "seller/info";
    }
    //将输入的修改数据更新到数据库里面
    @PostMapping("/getInfo/update")
    public String updateMessage(SellerInfo seller_info){
        if(seller_info.getSellerId()==-1)seller_info.setUser(sellerinfoService.getUser(seller_info.getUser().getUserId()).get(0));
        sellerinfoService.updateSeller(seller_info);
        return "redirect:/seller/getInfo/"+seller_info.getUser().getUserId();
    }
    //借卖方的钱包查看
    @GetMapping("/sellerAccount")
    public String sellerAccount(Model model,HttpSession session){
        //根据session的内容判断钱包是否已经初始化
        Users user = (Users)session.getAttribute("user");
        Account account = accountService.findByUserId(user.getUserId());
        if(account == null){
            Account newAccount = new Account();
            newAccount.setBalance(new Float(0));
            newAccount.setPayPwd("");
            newAccount.setUser(user);
            account = accountService.update(newAccount);
        }
        if(account.getPayPwd().equals("") && account.getBalance() == 0){
            model.addAttribute("account",account);
            return "/seller/newAccount";
        }else{
            model.addAttribute("selleraccount",account);
            return "/seller/sellerAccount";
        }
    }
    @PostMapping("/sellerAccount")
    public String updateAccount(Account account, Model model){
        Account newaccount = accountService.update(account);
        model.addAttribute("selleraccount",newaccount);
        return "/seller/sellerAccount";
    }
    @GetMapping("/withdraw")
    //访问提现页面
    public String getWithdraw(Model model,HttpSession session){
        //查找账户的信息
        Users user = (Users)session.getAttribute("user");
        Account account = accountService.findByUserId(user.getUserId());
        model.addAttribute("account",account);
        return "/seller/withdraw";
    }
    @PostMapping("/withdraw")
    public String doWithdraw(Account account,Model model,HttpSession session){
        Account acnt = accountService.findByUserId(account.getUser().getUserId());
        if(acnt.getPayPwd().equals(account.getPayPwd())){
            if(acnt.getBalance() >= account.getBalance() && account.getBalance() >= 0){
                if(account.getBalance() > 0){
                    //创建流水单
                    Record record = new Record();
                    record.setUsers(acnt.getUser());
                    record.setOp(acnt.getUser());
                    record.setMoney(account.getBalance());
                    record.setTime(new Date());
                    record.setStatus("待审核");
                    record.setType("提现");
                    recordService.create(record);
                    return "redirect:/seller/sellerAccount";
                }else{
                    model.addAttribute("error","提现金额必须大于0！");
                    return "/seller/withdraw";
                }
            }else{
                model.addAttribute("error","余额不足！");
                return "/seller/withdraw";
            }
        }else {
            if(account.getPayPwd().equals(""))
                model.addAttribute("error","密码不能为空！");
            else
                model.addAttribute("error","密码错误！");
            return "/seller/withdraw";
        }
    }
    @GetMapping("/recharge")
    //访问充值页面
    public String getRecharge(Model model,HttpSession session){
        //查找账户的信息
        Users user = (Users)session.getAttribute("user");
        Account account = accountService.findByUserId(user.getUserId());
        model.addAttribute("account",account);
        return "/seller/recharge";
    }
    @PostMapping("/recharge")
    public String doRecharge(Account account,Model model,HttpSession session){
        Account acnt = accountService.findByUserId(account.getUser().getUserId());
        if(acnt.getPayPwd().equals(account.getPayPwd())){
                if(account.getBalance() > 0){
                    //创建流水单
                    Record record = new Record();
                    record.setUsers(acnt.getUser());
                    record.setOp(acnt.getUser());
                    record.setMoney(account.getBalance());
                    record.setTime(new Date());
                    record.setStatus("待审核");
                    record.setType("充值");
                    recordService.create(record);
                    return "redirect:/seller/sellerAccount";
                }else{
                    model.addAttribute("error","充值金额必须大于0！");
                    return "/seller/recharge";
                }
        }else {
            if(account.getPayPwd().equals(""))
                model.addAttribute("error","密码不能为空！");
            else
                model.addAttribute("error","密码错误！");
            return "/seller/recharge";
        }
    }
    //流水表
    @GetMapping("/record")
    public String withdrawRecord(HttpSession session, Model model){
        Users users = (Users) session.getAttribute("user");
        List<Record> records = recordService.findAllByUser_UserId(users.getUserId());
        if(records.isEmpty())
            model.addAttribute("empty","无");
        model.addAttribute("id", users.getUserId());
        model.addAttribute("records", records);
        return "/seller/record";
    }
    @GetMapping("/sellerorder")
    public String getSellerorder(HttpSession session,Model model){
        Users user = (Users)session.getAttribute("user");
        List<Stores> stores = storesService.findAllByUser_UserId(user.getUserId());
        model.addAttribute("storelist",stores);
        model.addAttribute("status","0");
        model.addAttribute("storestatue","0");
        //先查询所有订单
        List<OrderInfo> orders = orderService.findAllByStore_User_UserId(user.getUserId());
        model.addAttribute("orders",orders);
        return "/seller/sellerorder";
    }
    //根据下拉列表里面的内容查询订单
    @PostMapping("/search")
    public String getSearch(@RequestParam("status")String status,@RequestParam("stores")String stores, HttpSession session,Model model){
        if(stores.equals("0")&&status.equals("0"))return "redirect:/seller/sellerorder";
        String statue="0";
        if(status.equals("1"))statue="已支付";
        if(status.equals("2"))statue="待退款";
        if(status.equals("3"))statue="待发货";
        if(status.equals("4"))statue="已发货";
        if(status.equals("5"))statue="已完成";
        if(status.equals("6"))statue="已取消";
        List<Stores> storelist = storesService.findAllByUser_UserId(((Users)session.getAttribute("user")).getUserId());
        model.addAttribute("storelist",storelist);
        List<OrderInfo> orders;
        if(status.equals("0")){
            orders = orderService.findAllByStore_StoreId(Integer.parseInt(stores));
        }else if(stores.equals("0")){
            orders = orderService.findAllByStatus(statue);
        }else{
            orders = orderService.findAllByStatusAndStore_StoreId(statue,Integer.parseInt(stores));
        }
        model.addAttribute("orders",orders);
        model.addAttribute("status",status);
        model.addAttribute("storestatue",stores);
        return "/seller/sellerorder";
    }
    @GetMapping("/search/{status}/{stores}")
    public String regetSearch(@PathVariable("status")String status,@PathVariable("stores")String stores, HttpSession session,Model model){
        if(stores.equals("0")&&status.equals("0"))return "redirect:/seller/sellerorder";
        String statue="0";
        if(status.equals("1"))statue="已支付";
        if(status.equals("2"))statue="待退款";
        if(status.equals("3"))statue="待发货";
        if(status.equals("4"))statue="已发货";
        if(status.equals("5"))statue="已完成";
        if(status.equals("6"))statue="已取消";
        List<Stores> storelist = storesService.findAllByUser_UserId(((Users)session.getAttribute("user")).getUserId());
        model.addAttribute("storelist",storelist);
        List<OrderInfo> orders;
        if(status.equals("0")){
            orders = orderService.findAllByStore_StoreId(Integer.parseInt(stores));
        }else if(stores.equals("0")){
            orders = orderService.findAllByStatus(statue);
        }else{
            orders = orderService.findAllByStatusAndStore_StoreId(statue,Integer.parseInt(stores));
        }
        model.addAttribute("orders",orders);
        model.addAttribute("status",status);
        model.addAttribute("storestatue",stores);
        return "/seller/sellerorder";
    }
    //将已支付订单改为发货状态
    @GetMapping("/delivery/{id}/{status}/{stores}")
    public String delivery(@PathVariable("id")Integer id,@PathVariable("status")String status,@PathVariable("stores")String stores){
        //根据id获得对应的订单
        OrderInfo order = orderService.findByOrderId(id);
        order.setStatus("待发货");
        orderService.update(order);
        return "redirect:/seller/search/"+status+"/"+stores;
    }
    //将退款订单改为已取消状态
    @GetMapping("/drawback/{id}/{status}/{stores}")
    public String drawback(@PathVariable("id")Integer id,@PathVariable("status")String status,@PathVariable("stores")String stores,HttpSession session){
        //根据订单id获得对应的订单
        OrderInfo order = orderService.findByOrderId(id);
        //根据订单的数量和商品的单价，判断商品在品牌商那里的价格
        //获得商品的数量
        int quantity = order.getQuantity();
        //通过商品id找到品牌商中的商品信息
        BrandRepos brandRepos = reposService.findByGoodId(order.getGoods().getGoodId());
        //获得商品原有的单价
        float price = brandRepos.getPrice();
        float money = price*quantity;
        //根据借卖方id找到借卖方钱包
        Account account = accountService.findByUserId(((Users)session.getAttribute("user")).getUserId());
        float result = account.getBalance()-money;
        account.setBalance(result);
        accountService.update(account);
        order.setStatus("已取消");
        orderService.update(order);
        return "redirect:/seller/search/"+status+"/"+stores;
    }
    //将已发货的订单改为已完成状态
    @GetMapping("/done/{id}/{status}/{stores}")
    public String done(@PathVariable("id")Integer id,@PathVariable("status")String status,@PathVariable("stores")String stores,HttpSession session){
        //根据订单id获得相应订单
        OrderInfo order = orderService.findByOrderId(id);
        //根据订单的数量和商品的单价，判断商品在品牌商那里的价格
        //获得商品的数量
        int quantity = order.getQuantity();
        //通过商品id找到品牌商中的商品信息
        BrandRepos brandRepos = reposService.findByGoodId(order.getGoods().getGoodId());
        //获得商品原有的单价
        float price = brandRepos.getPrice();
        float money = price*quantity;
        //根据借卖方id找到借卖方钱包
        Account account = accountService.findByUserId(((Users)session.getAttribute("user")).getUserId());
        float result = account.getBalance()-money;
        account.setBalance(result);
        accountService.update(account);
        //将钱转给品牌商
        Account accountPin = accountService.findByUserId(brandRepos.getBrand().getUserId());
        float resultToPinpaishang = accountPin.getBalance()+money;
        accountPin.setBalance(resultToPinpaishang);
        accountService.update(accountPin);
        order.setStatus("已完成");
        orderService.update(order);
        return "redirect:/seller/search/"+status+"/"+stores;
    }
}
