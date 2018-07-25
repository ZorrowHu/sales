package com.platform.sales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("administrator")
public class AdministratorController {

    @GetMapping("/index")
    public String getAllOrders(){
        return "administrator/index";
    }

}
