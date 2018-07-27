package com.platform.sales.controller;

import com.platform.sales.entity.*;
import com.platform.sales.repository.*;
import com.platform.sales.surface.*;
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
    @Autowired
    private BrandInfoRepository brandInfoRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SellerinfoRepository sellerinfoRepository;
    @Autowired
    private BrandReposRepository brandReposRepository;
    @Autowired
    private ShipAddrRepository shipAddrRepository;
    @Autowired
    private BrandAccountRepository brandAccountRepository;
    @Autowired
    private BrandOrderRepository brandOrderRepository;
    @Autowired
    private StoregoodsRepository storegoodsRepository;
    @Autowired
    private StoresRepository storesRepository;

    /**
     * 跳转到管理员首页
     * @return
     */
    @GetMapping("/index")
    public String index(){
        return "administrator/index";
    }


/**
 * 以下为流水信息管理的方法
 */

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
 * 以上为流水信息方法
 * 以下为商品类型管理方法
 */

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
     * 跳转到增加类型信息页面
     * @return
     */
    @GetMapping("/addType")
    public String addTypePage(){
        return "administrator/addType";
    }

    /**
     * 通过前端表单提交的数据，增加类型
     * @param type
     * @return
     */
    @PostMapping("/addType")
    public String addType(Type type){
        typeService.addType(type);
        return "redirect:/administrator/type";
    }

    /**
     * 通过id删除类型
     * @param id
     * @return
     */
    @GetMapping("/deleteType/{id}")
    public String deleteType(@PathVariable("id") Integer id){
        typeService.deleteById(id);
        return "redirect:/administrator/type";
    }

    /**
     * 跳转到更改类型页面
     * @param id
     * @param model
     * @return
     */
    @GetMapping("updateType/{id}")
    public String modifTypePage(@PathVariable("id") Integer id,
                           Model model){
        Type type = typeService.findById(id);
        model.addAttribute("Type",type);
        return "administrator/updateType";
    }

    /**
     * 保存表单提交的修改过后的类型信息
     * @param type
     * @return
     */
    @PostMapping("updateType")
    public String modifType(Type type ){
        typeService.updateType(type);
        return "redirect:/administrator/type";
    }



/**
 * 以上为商品类型信息管理方法
 * 以下为品牌商角色管理方法
 */

    /**
     * 跳转到品牌商角色管理页
     * @param model
     * @return
     */
    @GetMapping("/brand")
    public String brandIndex(Model model){
        List<BrandInfo> brands = brandInfoRepository.findAll();
        model.addAttribute("Brands", brands);
        return "administrator/brand";
    }

    /**
     * 根据id查找并显示需要更改的品牌商角色信息
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/updateBrand/{id}")
    public String updateBrandPage(@PathVariable("id") Integer id,
                                  Model model){
        BrandInfo brand = brandInfoRepository.findById(id).get();
        model.addAttribute("brand", brand);
        return "administrator/updateBrand";
    }

    /**
     * 保存修改后的品牌商角色信息并跳转回品牌商角色管理页
     * @param brandId
     * @param userName
     * @param password
     * @param brandName
     * @param brandDesc
     * @return
     */
    @PostMapping("/updateBrand")
    public String updateBrand(@RequestParam Integer brandId,
                              @RequestParam String userName,
                              @RequestParam String password,
                              @RequestParam String brandName,
                              @RequestParam String brandDesc){
        BrandInfo brand = brandInfoRepository.findById(brandId).get();  // 找到对应的品牌商
        Users user = usersRepository.findById(brand.getUsers().getUserId()).get(); // 找到对应的用户
        // 设置更改后的品牌商角色信息并保存
        brand.setBrName(brandName);
        brand.setBrDescription(brandDesc);
        user.setUserName(userName);
        user.setPassword(password);
        brandInfoRepository.save(brand);
        usersRepository.save(user);
        return "redirect:/administrator/brand";
    }

    /**
     * 根据id删除对应的角色信息，并且需要删除数据库中所有与其相关的裙带数据
     * @param id
     * @return
     */
    @GetMapping("/deleteBrand/{id}")
    public String deleteBrand(@PathVariable("id") Integer id){
        BrandInfo brand = brandInfoRepository.findById(id).get();
        Users user = usersRepository.findById(brandInfoRepository.findById(id).get().getUsers().getUserId()).get();
        brandOrderRepository.deleteAllByGoods_Brand(user);      // 删除其对应的订单数据
        brandAccountRepository.deleteByUser(user);              // 删除品牌商对应的钱包
        shipAddrRepository.deleteAllByUsers(user);              // 删除品牌商对应的收货地址信息
        recordAdminRepository.deleteAllByUsersOrOp(user, user); // 删除品牌商对应的流水信息
        brandReposRepository.deleteAllByBrand(user);            // 删除品牌商对应的商品
        usersRepository.delete(user);                           // 删除品牌商对应的角色
        brandInfoRepository.delete(brand);                      // 删除品牌商信息
        return "redirect:/administrator/brand";
    }



/**
 * 以上为品牌商角色管理方法
 * 以下为借卖方角色管理方法
 */

    /**
     * 跳转到借卖方角色管理页并显示所有借卖方角色
     * @param model
     * @return
     */
    @GetMapping("/seller")
    public String sellerIndex(Model model){
        List<SellerInfo> sellers = sellerinfoRepository.findAll();
        model.addAttribute("sellers", sellers);
        return "administrator/seller";
    }

    /**
     * 根据id查找需要修改的借卖方信息并显示
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/updateSeller/{id}")
    public String updateSellerPage(@PathVariable("id") Integer id,
                                   Model model){
        SellerInfo seller = sellerinfoRepository.findById(id).get();
        model.addAttribute("seller", seller);
        return "administrator/updateSeller";
    }

    @PostMapping("/updateSeller")
    public String updateSeller(@RequestParam Integer sellerId,
                               @RequestParam String userName,
                               @RequestParam String password,
                               @RequestParam String sellerName,
                               @RequestParam String sellerEmail,
                               @RequestParam String sellerPhone){
        SellerInfo seller = sellerinfoRepository.findById(sellerId).get();
        Users user = usersRepository.findById(seller.getUser().getUserId()).get();
        seller.setUserName(sellerName);
        seller.setMail(sellerEmail);
        seller.setPhone(sellerPhone);
        user.setUserName(userName);
        user.setPassword(password);
        sellerinfoRepository.save(seller);
        usersRepository.save(user);
        return "redirect:/administrator/seller";
    }

    /**
     * 根据id删除对应的角色信息，并且需要删除数据库中所有与其相关的裙带数据
     * @param id
     * @return
     */
    @GetMapping("/deleteSeller/{id}")
    public String deleteSeller(@PathVariable("id") Integer id){
        SellerInfo seller = sellerinfoRepository.findById(id).get();
        Users user = usersRepository.findById(seller.getUser().getUserId()).get();
        recordAdminRepository.deleteAllByUsersOrOp(user, user); // 删除借卖方对应的流水信息
        shipAddrRepository.deleteAllByUsers(user);              // 删除借卖方对应的地址信息
        brandAccountRepository.deleteByUser(user);              // 删除借卖方对应的钱包
        storegoodsRepository.deleteAllByStores_User(user);      // 删除借卖方对应的所有货物
        storesRepository.deleteAllByUser(user);                 // 删除借卖方对应的所有商店
        usersRepository.delete(user);                           // 删除借卖方对应的角色
        sellerinfoRepository.delete(seller);                    // 删除借卖方信息
        return "redirect:/administrator/seller";
    }

}
