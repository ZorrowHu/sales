package com.platform.sales.controller;
import com.platform.sales.entity.Stores;
import com.platform.sales.entity.Users;
import com.platform.sales.surface.StoregoodsService;
import com.platform.sales.surface.StoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
@Controller
@RequestMapping("Stores")
    public class StoresController {
@Autowired
    private StoresService storesService;

    @GetMapping("/addstore")
    public String addPage(){

        return "Stores/addstore";
    }

    @PostMapping("/addstore")
    public String addStore(Stores stores,HttpSession session)
    {
        Users user= (Users)session.getAttribute("user");
        stores.setUser(user);
        storesService.addStoreInfo(stores);
        return "redirect:/Stores/storeindex";
    }
    @GetMapping("/storeindex")
    public String getindex(Model model,HttpSession session){
        Users user = (Users)session.getAttribute("user");
        List<Stores> lists=storesService.findAllByUser_UserId(user.getUserId());
        model.addAttribute("stus",lists);
        return "Stores/storeindex";
    }

    @GetMapping("delete/{id}")
    public String deleteStu(@PathVariable("id") Integer store_id){
        storesService.deleteByStoreID(store_id);
        return "redirect:/Stores/storeindex"; // 删除完成后生定向到首页即：列表页面（index）
    }

    /**
     * 根据主键找到要修改的记录，并通过Model将数据传给updateStu页面，并显示在文本框中。
     * @param store_id
     * @param model
     * @return
     */
    @GetMapping("/updatestore/{id}")
    public String modifStore(@PathVariable("id") Integer store_id, Model model){
        Stores stores=storesService.getStoreByID(store_id);
        model.addAttribute("stu",stores);
        return "Stores/updatestore";
    }

    /**
     * 接受修改的数据并进行修改操作。
     * @param stores
     * @return
     */
    @PostMapping("updatestore")
    public String modifStu(Stores stores,HttpSession session ){
        Users user = (Users)session.getAttribute("user");
        stores.setUser(user);
        storesService.updateStudentInfo(stores);
        return "redirect:/Stores/storeindex"; // 修改完成后重定向到首页即：列表页面（index）
    }

}
