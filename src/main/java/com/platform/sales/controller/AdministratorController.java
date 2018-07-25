package com.platform.sales.controller;

import com.platform.sales.entity.Record;
import com.platform.sales.entity.Type;
import com.platform.sales.repository.RecordAdminRepository;
import com.platform.sales.surface.TypeService;
import com.platform.sales.surface.UsersService;
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
    @Autowired
    private RecordAdminRepository recordAdminRepository;

    /**
     * 跳转到管理员首页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "administrator/index";
    }

    @GetMapping("/cash/{status}")
    public String getCashs(@PathVariable("status") String status,
                           Model model){
        if (status == "全部"){    // 当参数为全部时显示所有的流水
            List<Record> records = recordAdminRepository.findAll();
            model.addAttribute("Records", records);
        }else{  // 根据传递的状态参数显示流水
            List<Record> records = recordAdminRepository.findByStatus(status);
            model.addAttribute("Records", records);
        }
        return "administrator/cash";
    }

    /**
     * 跳转到商品类型信息管理的首页，并加载所有类型
     * @param model
     * @return
     */
    @GetMapping("/type")
    public String getTypes(Model model){
        List<Type> lists = typeService.getAllTypes();
        model.addAttribute("Types", lists);
        return "administrator/type";
    }

    /**
     * 跳转到增加信息页面
     * @return
     */
    @GetMapping("/add")
    public String addPage(){
        return "administrator/add";
    }

    /**
     * 通过前端表单提交的数据，增加类型
     * @param type
     * @return
     */
    @PostMapping("/add")
    public String addType(Type type){
        typeService.addType(type);
        return "redirect:/administrator/type";
    }

    /**
     * 通过id删除类型
     * @param id
     * @return
     */
    @GetMapping("/delete/{id}")
    public String deleteType(@PathVariable("id") Integer id){
        typeService.deleteById(id);
        return "redirect:/administrator/type";
    }

    /**
     * 跳转到更改页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("update/{id}")
    public String modifStu(@PathVariable("id") Integer id,
                           Model model){
        Type type = typeService.findById(id);
        model.addAttribute("Type",type);
        return "administrator/update";
    }

    /**
     * 保存表单提交的修改过后的信息
     * @param type
     * @return
     */
    @PostMapping("update")
    public String modifType(Type type ){
        typeService.updateType(type);
        return "redirect:/administrator/type";
    }

}
