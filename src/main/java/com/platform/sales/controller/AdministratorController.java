package com.platform.sales.controller;

import com.platform.sales.entity.Account;
import com.platform.sales.entity.Record;
import com.platform.sales.entity.Type;
import com.platform.sales.repository.RecordAdminRepository;
import com.platform.sales.surface.BrandAccountService;
import com.platform.sales.surface.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("administrator")
public class AdministratorController {

    @Autowired
    private TypeService typeService;
    @Autowired
    private BrandAccountService accountService;
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

    /**
     * 根据参数status查找对应的流水信息并显示
     * @param status
     * @param model
     * @return
     */
    @RequestMapping("/cash")
    public String getCashs(@RequestParam(value = "status", required = false, defaultValue = "全部") String status,
                           Model model){
        if (status.equals("全部")){    // 当参数为全部时显示所有的流水
            List<Record> records = recordAdminRepository.findAll();
            model.addAttribute("Records", records);
        }else{  // 根据传递的状态参数显示流水
            List<Record> records = recordAdminRepository.findAllByStatus(status);
            model.addAttribute("Records", records);
        }
        return "administrator/cash";
    }

    /**
     * 通过id查找流水，并完成状态更改以及对应账户的余额增加
     * @param id
     * @return
     */
    @GetMapping("pass/{id}")
    public String pass(@PathVariable("id") Integer id,
                       RedirectAttributes redirectAttributes){
        Record record = recordAdminRepository.findById(id).get();   // 找到对应的财务流水
        Account account = accountService.findByUserId(record.getOp().getUserId());  // 找到对应用户的钱包
        switch (record.getType()){
            case "充值":
                account.setBalance(account.getBalance() + record.getMoney());   // 充值：钱包余额增加
                record.setStatus("已通过");    // 将操作完成的流水状态设置为已通过
                break;
            case "提现":   // 提现：余额充足时可提现，余额不足时不通过
                if (account.getBalance() < record.getMoney()){
                    redirectAttributes.addFlashAttribute("message", "该用户余额不足，禁止提现,已默认不通过！");
                    redirectAttributes.addFlashAttribute("ErrId", id + "");
                    record.setStatus("未通过");
                }else {
                    account.setBalance(account.getBalance() - record.getMoney());
                    record.setStatus("已通过");    // 将操作完成的流水状态设置为已通过
                }
                break;
        }
        recordAdminRepository.save(record); // 将改变状态后的流水信息存表
        return "redirect:/administrator/cash";
    }

    /**
     * 通过id查找流水，并完成状态更改以及对应账户的余额减少
     * @param id
     * @return
     */
    @GetMapping("fail/{id}")
    public String fail(@PathVariable("id") Integer id){
        Record record = recordAdminRepository.findById(id).get();   // 找到对应的财务流水
        record.setStatus("未通过");    // 将操作完成的流水状态设置为未通过
        recordAdminRepository.save(record); // 将改变状态后的流水信息存表
        return "redirect:/administrator/cash";
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
