package com.platform.sales.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("brand")
public class BrandreposController {
        @RequestMapping(value = "/index")
        public String index(@RequestParam(value="keyword", defaultValue="testing") String keyword, Model model) {
                if(keyword == "")
                {
                        model.addAttribute("Message", "No keyword");
                }else{
                        model.addAttribute("Message", keyword);
                }
                return "brand/index";
        }
        @GetMapping("/mainframe")
        public String mainframe() {
                return "brand/mainframe";
        }
}
