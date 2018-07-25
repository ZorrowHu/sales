package com.platform.sales.controller;

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
        public Map<String, Object> getsecondary(String primary){
                Map<String, Object> jsonMap = new HashMap<String, Object>();
                List<String> secondaries = typeRepository.getSecondary(primary);
                List list = new ArrayList();
                ;

                for(String secondary : secondaries){
                        Map<String,String> taskMap=new HashMap<String,String>();

                        taskMap.put("primary", primary);
                        taskMap.put("secondary", secondary);
                        list.add(taskMap);
                        //list.add(taskMap);
                }
                jsonMap.put("tasks", list);
                return jsonMap;
        }
}
