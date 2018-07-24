package com.platform.sales.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("index")
public class IndexController {

        @GetMapping("/index")
        public String index(){
                return "index";
        }

        @GetMapping("/login")
        public String login(){
                return  "login";
        }

        @GetMapping("/register")
        public String register(){
                return "register";
        }
}
