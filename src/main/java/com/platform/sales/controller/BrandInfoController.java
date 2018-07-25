package com.platform.sales.controller;

import com.platform.sales.entity.Account;
import com.platform.sales.entity.BrandInfo;
import com.platform.sales.entity.Record;
import com.platform.sales.surface.BrandAccountService;
import com.platform.sales.surface.BrandInfoService;
import com.platform.sales.surface.BrandRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    //我的信息
    @GetMapping("/brandinfo/{id}")
    public String brandInfo(@PathVariable("id") Integer id, Model model){
        BrandInfo brandInfo = brandInfoService.findByUserId(id);
        model.addAttribute("brandinfo",brandInfo);
        return "/brand/brandinfo";
    }

    @PostMapping("/updateBrandinfo")
    public String updateBrandInfo(BrandInfo brandInfo){
        BrandInfo newbrandInfo = brandInfoService.update(brandInfo);
        return "redirect:/brand/brandinfo/" + newbrandInfo.getUsers().getUserId();
    }

    //我的钱包
    @GetMapping("/brandaccount/{id}")
    public String brandAccount(@PathVariable("id") Integer id, Model model){
        Account account = brandAccountService.findByUserId(id);
        model.addAttribute("brandaccount",account);
        return "/brand/brandaccount";
    }

    //提现
    @GetMapping("/withdraw/{id}")
    public String withdraw(@PathVariable("id") Integer id, Model model){
        Account account = brandAccountService.findByUserId(id);
        model.addAttribute("account", account);
        return "/brand/withdraw";
    }

    @PostMapping("withdraw")
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
                brandRecordService.create(record);
                return "redirect:/brand/brandaccount/" + acnt.getUser().getUserId();
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

    @GetMapping("/withdrawrecord/{id}")
    public String withdrawRecord(@PathVariable("id") Integer id, Model model){
        List<Record> records = brandRecordService.findByUserAndOp(id);
        if(records.isEmpty())
            model.addAttribute("empty","无");
        model.addAttribute("id", id);
        model.addAttribute("records", records);
        return "/brand/withdrawrecord";
    }

}
