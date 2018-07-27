package com.platform.sales.controller;

import com.platform.sales.entity.*;
import com.platform.sales.repository.BrandReposRepository;
import com.platform.sales.surface.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
                String pathName = "src/main/resources/static/brandimg/";
                Long stamp = new Date().getTime();
                String prefix = stamp.toString();
                String fileName = prefix + file.getOriginalFilename();

                brandInfo.setImage(fileName);

                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(pathName + fileName)));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                //return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                //return "上传失败," + e.getMessage();
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

        List<Record> records_1 = brandRecordService.findByUser(users);
        List<Record> records_2 = brandRecordService.findByOp(users);
        List<Record> records = brandRecordService.findByUserAndOp(users.getUserId());
        records.addAll(records_1);
        records.addAll(records_2);
//        for (int i = 0; i < records.size(); i++){
//            Record record = records.get(i);
//            if(record.getUsers().getUserId() == users.getUserId()
//                    && record.getOp().getUserId() == users.getUserId()){
//                record.setType("提现");
//            }else{
//                record.setType("转账");
//            }
//        }
        if(records.isEmpty())
            model.addAttribute("empty","无");
        model.addAttribute("id", users.getUserId());
        model.addAttribute("records", records);
        return "/brand/withdrawrecord";
    }

    //订单管理
    @GetMapping("/brandorder")
    public String brandOrder(HttpSession session, Model model){
        Users user = (Users) session.getAttribute("user");
        List<BrandRepos> goods = brandReposRepository.findAllByBrand(user);
        List<OrderInfo> brandOrder = new ArrayList<OrderInfo>();
        for(int i = 0; i < goods.size(); i++){
            List<OrderInfo> order = brandOrderService.findByGoods(goods.get(i));
            brandOrder.addAll(order);
        }
        model.addAttribute("brandorder",brandOrder);
        return "/brand/brandorder";
    }

    @RequestMapping(value = "/selectorder",method = RequestMethod.POST)
    public String selectOrder(@RequestParam("status") String select, HttpSession session,Model model){
        Users user = (Users) session.getAttribute("user");
        List<BrandRepos> goods = brandReposRepository.findAllByBrand(user);
        List<OrderInfo> brandOrder = new ArrayList<OrderInfo>();
        if(select.equals("0")){
            for(int i = 0; i < goods.size(); i++){
                List<OrderInfo> order = brandOrderService.findByGoods(goods.get(i));
                brandOrder.addAll(order);
            }
            model.addAttribute("brandorder",brandOrder);
            model.addAttribute("select", select);
            return "/brand/brandorder";
        }else {
            for (int i = 0; i < goods.size(); i++) {
                List<OrderInfo> order = brandOrderService.findByStatusAndGoods(select, goods.get(i));
                brandOrder.addAll(order);
            }
            model.addAttribute("brandorder",brandOrder);
            model.addAttribute("select", select);
            return "/brand/brandorder";
        }
    }

    //订单发货
    @GetMapping("/deliverorder/{id}")
    public String deliverOrder(@PathVariable("id") Integer id,HttpSession session,Model model){
        OrderInfo orderInfo = brandOrderService.findByOrderId(id);
        orderInfo.setStatus("已发货");
        brandOrderService.update(orderInfo);
        Users user = (Users) session.getAttribute("user");
        List<BrandRepos> goods = brandReposRepository.findAllByBrand(user);
        List<OrderInfo> brandOrder = new ArrayList<OrderInfo>();
        for(int i = 0; i < goods.size(); i++){
            List<OrderInfo> order = brandOrderService.findByGoods(goods.get(i));
            brandOrder.addAll(order);
        }
        model.addAttribute("brandorder",brandOrder);
        return "/brand/brandorder";
    }

   // 订单取消
   @GetMapping("/cancelorder/{id}")
   public String cancelOrder(@PathVariable("id") Integer id,HttpSession session,Model model){
       OrderInfo orderInfo = brandOrderService.findByOrderId(id);
       orderInfo.setStatus("已取消");
       brandOrderService.update(orderInfo);
       Users user = (Users) session.getAttribute("user");
       List<BrandRepos> goods = brandReposRepository.findAllByBrand(user);
       List<OrderInfo> brandOrder = new ArrayList<OrderInfo>();
       for(int i = 0; i < goods.size(); i++){
           List<OrderInfo> order = brandOrderService.findByGoods(goods.get(i));
           brandOrder.addAll(order);
       }
       model.addAttribute("brandorder",brandOrder);
       return "/brand/brandorder";
   }
}
