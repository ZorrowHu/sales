package com.platform.sales.controller;

import com.platform.sales.entity.SellerInfo;
import com.platform.sales.entity.Users;
import com.platform.sales.surface.SellerinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("seller")
public class SellerinfoController {
    @Autowired
    SellerinfoService sellerinfoService;
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
    @PostMapping("/getInfo/update")
    public String updateMessage(SellerInfo seller_info){
        if(seller_info.getSellerId()==-1)seller_info.setUser(sellerinfoService.getUser(seller_info.getUser().getUserId()).get(0));
        sellerinfoService.updateSeller(seller_info);
        return "redirect:/seller/getInfo/"+seller_info.getUser().getUserId();
    }
}
