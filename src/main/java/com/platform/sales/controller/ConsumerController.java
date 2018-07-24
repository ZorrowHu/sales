package com.platform.sales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("consumer")
public class ConsumerController {

    @GetMapping("/index")
    public String index(){
        return "consumer/index";
    }

    @GetMapping("/login")
    public String login(){
        return  "consumer/login";
    }

    @GetMapping("/register")
    public String register(){
        return "consumer/register";
    }

}
