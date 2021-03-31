package com.platform.sales.controller;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandReposRepository;
import com.platform.sales.repository.TypeRepository;
import com.platform.sales.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brandrest")
public class BrandreposRestController {

        @Autowired
        BrandReposRepository brandReposRepository;
        @Autowired
        TypeRepository typeRepository;
        @Autowired
        UsersRepository usersRepository;

        @PostMapping("/getsecondary")
        public List<String> getsecondary(String primary){
                List<String> secondaries = typeRepository.getSecondary(primary);
                return secondaries;
        }
        @PostMapping("/gettertiary")
        public List<String> gettertiary(String primary, String secondary){
                List<String> tertiary = typeRepository.getTertiary(primary, secondary);
                return tertiary;
        }

        @GetMapping("/index")
        public HashMap<String, Object> index(Model model){
                List<Type> types = typeRepository.findAll();

                HashMap<String, Object> sum = new HashMap<String,  Object>();

                List<String> primaries = typeRepository.getPrimary();
                List<String> secondaries = typeRepository.getSecondary();
                List<String> tertiaries = typeRepository.getTertiary();

                HashMap<String, Object> primary = new HashMap<String,  Object>();
                for (String first : primaries){
                        HashMap<String, Object> secondary = new HashMap<String, Object>();
                        for (String second : secondaries){
                                HashMap<String, Integer> tertiary = new HashMap<String, Integer>();
                                for (String third : tertiaries){
                                        for (Type type : types){
                                                if (type.getContent3().equals(third) && type.getContent2().equals(second) && type.getContent1().equals(first))
                                                {
                                                        tertiary.put(type.getContent3(), type.getTypeId());
                                                        secondary.put(type.getContent2(), tertiary);
                                                        primary.put(type.getContent1(), secondary);
                                                }
                                        }
                                }
                        }
                }
//                for (Type type : types){
//                        tertiary.put(type.getContent3(), type.getTypeId());
//                        secondary.put(type.getContent2(), tertiary);
//                        primary.put(type.getContent1(), secondary);
//                }
                //model.addAttribute("types", primary);
                return primary;
        }
        @PostMapping("/getgoodsbytype")
        public List<BrandRepos> getgoodsbytype(String primary, String secondary, String tertiary, HttpSession session){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                Type type = typeRepository.findTypeByContent1AndContent2AndContent3(primary, secondary, tertiary);
                List<BrandRepos> goods = brandReposRepository.findBrandreposByTypeAndStatusAndBrand(type, "新入仓", user);
                return goods;
        }
}
