package com.platform.sales.controller;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.Type;
import com.platform.sales.repository.BrandReposRepository;
import com.platform.sales.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        @PostMapping("/getgoodsbytype")
        public List<BrandRepos> getgoodsbytype(String primary, String secondary, String tertiary){
                Type type = typeRepository.findTypeByContent1AndAndContent2AndContent3(primary, secondary, tertiary);
                List<BrandRepos> goods = brandReposRepository.findBrandreposByTypeAndStatus(type, "新入仓");
                return goods;
        }
}
