package com.platform.sales.controller;

import com.platform.sales.config.FileUtil;
import com.platform.sales.entity.*;
import com.platform.sales.repository.BrandOrderRepository;
import com.platform.sales.repository.BrandRecordRepository;
import com.platform.sales.repository.BrandReposRepository;
import com.platform.sales.surface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandInfoController {

    @Autowired
    BrandRecordRepository brandRecordRepository;
    @Autowired
    private BrandInfoService brandInfoService;

    @Autowired
    private BrandAccountService brandAccountService;

    @Autowired
    private BrandRecordService brandRecordService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private BrandReposRepository brandReposRepository;

    @Autowired
    private BrandOrderService brandOrderService;

    @Autowired
    private BrandOrderRepository brandOrderRepository;

    //我的信息
    @GetMapping("/brandinfo")
    public String brandInfo(HttpSession session,Model model){
        Users users = (Users) session.getAttribute("user");
        BrandInfo brandInfo = brandInfoService.findByUserId(users.getUserId());
        Account brandacnt = brandAccountService.findByUserId(users.getUserId());
        if(brandInfo == null){
            if(brandacnt != null)
                model.addAttribute("account", brandacnt.getAccountId());
            else
                model.addAttribute("account", "-1");
            return "/brand/newbrandinfo";
        }else{
            model.addAttribute("brandinfo",brandInfo);
            return "/brand/brandinfo";
        }
    }

    //修改信息
    @RequestMapping(value = "/updateBrandinfo",method = RequestMethod.POST)
    public String updateBrandInfo(
            @RequestParam("filamge") MultipartFile file,
            HttpSession session,
            BrandInfo brandInfo, Model model){
        //分配一个钱包
        if(brandInfo.getAccount().getAccountId() == -1){
            Users users = (Users) session.getAttribute("user");
            Account brandacnt = new Account();
            brandacnt.setBalance(new Float(0));
            brandacnt.setPayPwd("");
            brandacnt.setUser(users);
            Account acnt = brandAccountService.update(brandacnt);
            brandInfo.setAccount(acnt);
        }
        if (!file.isEmpty()) {
            try {

                String pathName = FileUtil.getUpLoadFilePath("brandimg/");

                Long stamp = new Date().getTime();
                String prefix = stamp.toString();
                String fileName = prefix + file.getOriginalFilename();

                FileUtil.uploadFile(file.getBytes(),pathName,fileName);

                brandInfo.setImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //return "上传成功";
        } else {
            //return "上传失败，因为文件是空的.";
        }
        BrandInfo newbrandInfo = brandInfoService.update(brandInfo);
        model.addAttribute("brandinfo", newbrandInfo);
        return "/brand/brandinfo";
    }

    //我的钱包
    @GetMapping("/brandaccount")
    public String brandAccount(HttpSession session, Model model){
        Users users = (Users) session.getAttribute("user");
        Account account = brandAccountService.findByUserId(users.getUserId());
        if(account == null){
            Account brandacnt = new Account();
            brandacnt.setBalance(new Float(0));
            brandacnt.setPayPwd("");
            brandacnt.setUser(users);
            account = brandAccountService.update(brandacnt);
        }
        if(account.getPayPwd().equals("")){
            model.addAttribute("account",account);
            return "/brand/newaccount";
        }else{
            model.addAttribute("brandaccount",account);
            return "/brand/brandaccount";
        }
    }

    //修改原始支付密码
    @PostMapping("/brandaccount")
    public String updateAccount(Account account, Model model){
        Account newaccount = brandAccountService.update(account);
        model.addAttribute("brandaccount",newaccount);
        return "/brand/brandaccount";
    }

    //提现
    @GetMapping("/withdraw")
    public String withdraw(HttpSession session, Model model){
        Users users = (Users) session.getAttribute("user");
        Account account = brandAccountService.findByUserId(users.getUserId());
        model.addAttribute("account", account);
        return "/brand/withdraw";
    }

    @PostMapping("/withdraw")
    public String withdraw(Account account,Model model){
        Account acnt = brandAccountService.findByUserId(account.getUser().getUserId());
        if(acnt.getPayPwd().equals(account.getPayPwd())){
            if(acnt.getBalance() >= account.getBalance() && acnt.getBalance() > 0){
                if(account.getBalance() > 0){
                    //创建流水单
                    Record record = new Record();
                    record.setUsers(acnt.getUser());
                    record.setOp(acnt.getUser());
                    record.setMoney(account.getBalance());
                    record.setTime(new Date());
                    record.setStatus("待审核");
                    record.setType("提现");
                    brandRecordService.create(record);
                    return "redirect:/brand/brandaccount";
                }else{
                    model.addAttribute("error","提现金额必须大于0！");
                    return "/brand/withdraw";
                }
            }else{
                if(account.getBalance() <= 0)
                    model.addAttribute("error","提现金额必须大于0！");
                else
                    model.addAttribute("error","余额不足！");
                return "/brand/withdraw";
            }
        }else {
            if(account.getPayPwd().equals(""))
                model.addAttribute("error","密码不能为空！");
            else
                model.addAttribute("error","密码错误！");
            return "/brand/withdraw";
        }
    }

    //流水表
    @GetMapping("/record")
    public String withdrawRecord(HttpSession session, Model model){
        Users users = (Users) session.getAttribute("user");

        List<String> orderString = new ArrayList<String>();

        List<Record> records_2 = brandRecordService.findByOp(users);
        List<Record> records = brandRecordService.findByUserAndOp(users.getUserId());
        records.addAll(records_2);
        for (int i = 0; i < records.size(); i++){
            Record record = records.get(i);
            if(record.getUsers().getUserId() == users.getUserId()
                    && record.getOp().getUserId() == users.getUserId()){
                record.setType("提现");
            }else{
                record.setType("转入");
            }
        }
        if(records.isEmpty()) {
            model.addAttribute("empty", "无");
        }

        else{
        }
        model.addAttribute("orderString","wu");


        model.addAttribute("id", users.getUserId());
        model.addAttribute("records", records);
        return "/brand/withdrawrecord";
    }

    //订单管理
    @GetMapping("/brandorder")
    public String brandOrder(HttpSession session, Model model){
        model.addAttribute("select","0");
        Users user = (Users) session.getAttribute("user");
        List<BrandRepos> goods = brandReposRepository.findAllByBrand(user);
        //6个字段用来保存各个状态包括总计的钱
        float yizhifumoney=0;
        float daifahuomoney=0;
        float yifahuomoney=0;
        float daituikuanmoney=0;
        float yiwanchengmoney=0;
        float yiquxiaomoney=0;
        List<OrderInfo> brandOrder = new ArrayList<OrderInfo>();
        for(int i = 0; i < goods.size(); i++){
            List<OrderInfo> order = brandOrderRepository.findAllByStatusNotAndGoods("待支付",goods.get(i));
            brandOrder.addAll(order);
        }
        //遍历，为money赋值
        for (OrderInfo bborder:brandOrder) {
            if(bborder.getStatus().equals("已支付"))yizhifumoney += bborder.getGoods().getPrice()*bborder.getQuantity();
            if(bborder.getStatus().equals("待发货"))daifahuomoney += bborder.getGoods().getPrice()*bborder.getQuantity();
            if(bborder.getStatus().equals("已发货"))yifahuomoney += bborder.getGoods().getPrice()*bborder.getQuantity();
            if(bborder.getStatus().equals("待退款"))daituikuanmoney += bborder.getGoods().getPrice()*bborder.getQuantity();
            if(bborder.getStatus().equals("已完成"))yiwanchengmoney += bborder.getGoods().getPrice()*bborder.getQuantity();
            if(bborder.getStatus().equals("已取消"))yiquxiaomoney += bborder.getGoods().getPrice()*bborder.getQuantity();
        }
        model.addAttribute("yizhifu",yizhifumoney);
        model.addAttribute("daifahuo",daifahuomoney);
        model.addAttribute("yifahuo",yifahuomoney);
        model.addAttribute("daituikuan",daituikuanmoney);
        model.addAttribute("yiwancheng",yiwanchengmoney);
        model.addAttribute("yiquxiao",yiquxiaomoney);
        model.addAttribute("brandorder",brandOrder);
        return "/brand/brandorder";
    }

    @RequestMapping(value = "/selectorder",method = RequestMethod.POST)
    public String selectOrder(@RequestParam("status") String select, HttpSession session,Model model,@RequestParam("yizhifu")float yizhifumoney
    ,@RequestParam("daifahuo")float daifahuomoney,@RequestParam("yifahuo")float yifahuomoney,@RequestParam("daituikuan")float daituikuanmoney
    ,@RequestParam("yiwancheng")float yiwanchengmoney,@RequestParam("yiquxiao")float yiquxiaomoney){
        model.addAttribute("yizhifu",yizhifumoney);
        model.addAttribute("daifahuo",daifahuomoney);
        model.addAttribute("yifahuo",yifahuomoney);
        model.addAttribute("daituikuan",daituikuanmoney);
        model.addAttribute("yiwancheng",yiwanchengmoney);
        model.addAttribute("yiquxiao",yiquxiaomoney);
        Users user = (Users) session.getAttribute("user");
        List<BrandRepos> goods = brandReposRepository.findAllByBrand(user);
        List<OrderInfo> brandOrder = new ArrayList<OrderInfo>();
        if(select.equals("0")){
            model.addAttribute("select","0");
            for(int i = 0; i < goods.size(); i++){
                List<OrderInfo> order = brandOrderRepository.findAllByStatusNotAndGoods("待支付",goods.get(i));
                brandOrder.addAll(order);
            }
            model.addAttribute("brandorder",brandOrder);
            return "/brand/brandorder";
        }else {
            if(select.equals("待发货"))model.addAttribute("select","待发货");
            if(select.equals("已发货"))model.addAttribute("select","已发货");
            if(select.equals("已完成"))model.addAttribute("select","已完成");
            if(select.equals("已取消"))model.addAttribute("select","已取消");
            for (int i = 0; i < goods.size(); i++) {
                List<OrderInfo> order = brandOrderService.findByStatusAndGoods(select, goods.get(i));
                brandOrder.addAll(order);
            }
            model.addAttribute("brandorder",brandOrder);
            return "/brand/brandorder";
        }
    }

    //订单发货
    @GetMapping("/deliverorder/{id}")
    public String deliverOrder(@PathVariable("id") Integer id,HttpSession session,Model model){
        OrderInfo orderInfo = brandOrderService.findByOrderId(id);
        BrandRepos repos = brandReposRepository.findByGoodId(orderInfo.getGoods().getGoodId());
        repos.setQuantity(repos.getQuantity() - orderInfo.getQuantity());
        brandReposRepository.save(repos);
        orderInfo.setStatus("已发货");
        brandOrderService.update(orderInfo);
        Users user = (Users) session.getAttribute("user");
        List<BrandRepos> goods = brandReposRepository.findAllByBrand(user);
        List<OrderInfo> brandOrder = new ArrayList<OrderInfo>();
        for(int i = 0; i < goods.size(); i++){
            List<OrderInfo> order = brandOrderRepository.findAllByStatusNotAndGoods("待支付",goods.get(i));
            brandOrder.addAll(order);
        }
        model.addAttribute("brandorder",brandOrder);
        return "/brand/brandorder";
    }

    @GetMapping("/xiangqing/{id}")
    public String getXiangqing(@PathVariable("id") Integer id,Model model){
        Record record = brandRecordRepository.findById(id).get();
        OrderInfo orderInfo = record.getOrderInfo();
        StringBuilder stringBuilder = new StringBuilder("订单编号:");
        stringBuilder.append(orderInfo.getOrderId());
        //订单编号	商品ID	商品名	消费者	数量	总价 创建时间	状态
        stringBuilder.append(";商品ID:"+orderInfo.getGoods().getGoodId());
        stringBuilder.append(";商品名:"+orderInfo.getGoods().getGoodName());
        stringBuilder.append(";消费者:"+orderInfo.getConsumer().getUserName());
        stringBuilder.append(";数量:"+orderInfo.getQuantity());
        stringBuilder.append(";总价:"+orderInfo.getTotalPrice());
        stringBuilder.append(";创建时间:"+orderInfo.getPayTime());
        stringBuilder.append(";状态:"+orderInfo.getStatus());
        model.addAttribute("string",stringBuilder.toString());
        return "/seller/xiangqing";
    }
}
