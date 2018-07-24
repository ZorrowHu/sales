package com.platform.sales.controller;

import com.platform.sales.entity.Brand_repos;
import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.Brand_reposRepository;
import com.platform.sales.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("brand")
public class BrandreposController {

        @Autowired
        Brand_reposRepository brand_reposRepository;
        @Autowired
        TypeRepository typeRepository;
        @GetMapping("/index")
        public String index(Model model) {
                List<Brand_repos> repository =  brand_reposRepository.findAll();
                model.addAttribute("Lists",  repository);
                return "brand/index";
        }

        @PostMapping("/index")
        public String index(String keyword, Model model) {
                List<Brand_repos> repository =  brand_reposRepository.findBrand_reposByGoodName(keyword);
                model.addAttribute("Lists",  repository);
                model.addAttribute("Message", "关键字搜索");
                return "brand/index";
        }

        @GetMapping("/newgoods")
        public String newgoods(){
                return "brand/newgoods";
        }

        @PostMapping("/addgoods")
        public String addgoods(Brand_repos brand_repos){
                Users brand = new Users();
                brand.setUser_id(1);
                brand_repos.setBrand(brand);            //default user_id
                brand_repos.setStatus("新入仓");       //default status

                Type type = typeRepository.findById(brand_repos.getType().getType_id()).get();
                brand_repos.setType(type);

                brand_reposRepository.save(brand_repos);
                return "redirect:/brand/index";
        }

        @GetMapping("/delgoods/{id}")
        public String delgoods(@PathVariable("id") Integer id){
                brand_reposRepository.deleteById(id);
                return "redirect:/brand/index";
        }

        @GetMapping("/uptgoods/{id}")
        public String uptgoods(@PathVariable("id") Integer id, Model model){
                Brand_repos good = brand_reposRepository.findById(id).get();
                model.addAttribute("good", good);
                return "brand/update";
        }

        @PostMapping("/doupdate/{id}")
        public String doupdate(@PathVariable("id") Integer id, Brand_repos good){
                good.setGoodId(id);

                Users brand = new Users();
                brand.setUser_id(1);
                good.setBrand(brand);            //default user_id
                good.setStatus("新入仓");       //default status
                
                Type type = typeRepository.findById(good.getType().getType_id()).get();
                good.setType(type);

                brand_reposRepository.save(good);
                return "redirect:/brand/index";
        }

        @GetMapping("/mainframe")
        public String mainframe() {
                return "brand/mainframe";
        }
}
