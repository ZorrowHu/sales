package com.platform.sales.controller;

import com.platform.sales.entity.Account;
import com.platform.sales.entity.BrandInfo;
import com.platform.sales.entity.Record;
import com.platform.sales.entity.Users;
import com.platform.sales.surface.BrandAccountService;
import com.platform.sales.surface.BrandInfoService;
import com.platform.sales.surface.BrandRecordService;
import com.platform.sales.surface.UsersService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    @PostMapping("/updateBrandinfo")
    public String updateBrandInfo(HttpSession session, BrandInfo brandInfo, Model model){
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
        if(account.getPayPwd().equals("") && account.getBalance() == 0){
            model.addAttribute("account",account);
            return "/brand/newaccount";
        }else{
            model.addAttribute("brandaccount",account);
            return "/brand/brandaccount";
        }
    }

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
            if(acnt.getBalance() >= account.getBalance()){
                Float rest = acnt.getBalance() - account.getBalance();
                acnt.setBalance(rest);
                brandAccountService.update(acnt);
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
                model.addAttribute("error","余额不足！");
                return "/brand/withdraw";
            }
        }else {
            if(account.getPayPwd().equals(""))
                model.addAttribute("error","密码不能为空！");
            else
                model.addAttribute("error","密码错误");
            return "/brand/withdraw";
        }
    }

    //流水表
    @GetMapping("/withdrawrecord")
    public String withdrawRecord(HttpSession session, Model model){
        Users users = (Users) session.getAttribute("user");
        List<Record> records = brandRecordService.findByUserAndOp(users.getUserId());
        if(records.isEmpty())
            model.addAttribute("empty","无");
        model.addAttribute("id", users.getUserId());
        model.addAttribute("records", records);
        return "/brand/withdrawrecord";
    }

    //订单管理
    @GetMapping("/brandorder")
    public String brandOrder(){
        return "/brand/brandorder";
    }
}
