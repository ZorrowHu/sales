package com.platform.sales.controller;

import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import com.platform.sales.surface.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("administrator")
public class AdministratorController {

    @Autowired
    private TypeService typeService;

    /**
     * 跳转到首页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "administrator/index";
    }

    @GetMapping("/cash")
    public String getCashs(){
        return "administrator/cash";
    }

    @GetMapping("/type")
    public String getTypes(Model model){
        List<Type> lists = typeService.getAllTypes();
        model.addAttribute("Types", lists);
        return "administrator/type";
    }

    @GetMapping("/add")
    public String addPage(){
        return "administrator/add";
    }

    @PostMapping("/add")
    public String addType(Type type){
        typeService.addType(type);
        return "redirect:/administrator/type";
    }

    @GetMapping("/delete/{id}")
    public String deleteType(@PathVariable("id") Integer id){
        typeService.deleteById(id);
        return "redirect:/administrator/type";
    }

    @GetMapping("update/{id}")
    public String modifStu(@PathVariable("id") Integer id,
                           Model model){
        Type type = typeService.findById(id);
        model.addAttribute("Type",type);
        return "administrator/update";
    }

    @PostMapping("update")
    public String modifType(Type type ){
        typeService.updateType(type);
        return "redirect:/administrator/type";
    }

}
