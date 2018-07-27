package com.platform.sales.controller;
import com.platform.sales.entity.StoreGoods;
import com.platform.sales.entity.Stores;
import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandReposRepository;
import com.platform.sales.repository.StoregoodsRepository;
import com.platform.sales.repository.TypeRepository;
import com.platform.sales.repository.UsersRepository;
import com.platform.sales.surface.BrandReposService;
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
    public class StoregoodsController {
        @Autowired
    private StoregoodsService storegoodsService;

        @Autowired
    private  StoresService storesService;

        @Autowired
        private BrandReposService brandReposService;

        @Autowired
        BrandReposRepository brandReposRepository;

        @Autowired
        TypeRepository typeRepository;

        @Autowired
        UsersRepository usersRepository;

    @GetMapping("ShowGoods/{id}")
    public String getGoods(Model model,@PathVariable("id") Integer store_id){
            List<StoreGoods> goodlists=storegoodsService.findAllByStores_StoreId(store_id);
            model.addAttribute("goods",goodlists);
            model.addAttribute("store_id", store_id);
            return "Stores/ShowGoods";
    }


    @GetMapping("/addgoods/{id}/{gid}")
    public String addGoodsindex(@PathVariable("id") Integer store_id,Model model,@PathVariable("gid") Integer good_id){
            model.addAttribute("id", store_id);
            return "Stores/addgoods";
    }


            //@RequestMapping(value="/addgoods/{id}", method=RequestMethod.POST)
            /*@PostMapping("/addgoods/{id}")
            public String addgoods(StoreGoods storeGoods,HttpSession session,@RequestParam("id") Integer store_id)
    {
            Stores stores=storesService.getStoreByID(store_id);
            storegoodsService.addGoodsInfo(storeGoods);
            return ("redirect:/Stores/ShowGoods/"+store_id);
   }*/

    @PostMapping("/addgoods")
    public String addgoods(StoreGoods storeGoods, Integer id){
            Stores stores=storesService.getStoreByID(id);
            storeGoods.setStores(stores);
            storegoodsService.addGoodsInfo(storeGoods);
            return "redirect:/Stores/ShowGoods/"+id;

    }


    @GetMapping("deletegoods/{id}")
    public String deletegoods(@PathVariable("id") Integer storegood_id,Integer id){
            StoreGoods storeGoods=storegoodsService.getGoodsByID(storegood_id);
            id=storeGoods.getStores().getStoreId();
            storegoodsService.deleteByGoodID(storegood_id);
            return "redirect:/Stores/ShowGoods/"+id; // 删除完成后生定向到首页即：列表页面（index）
    }


    @GetMapping("/updategoods/{id}")
    public String modifgoods(@PathVariable("id") Integer storegoodId, Model model){
            StoreGoods storeGoods=storegoodsService.getGoodsByID(storegoodId);
            model.addAttribute("good",storeGoods);
            return "Stores/updategoods";
    }


    @PostMapping("/updategoods")
    public String modifGoodss(StoreGoods storeGoods){
            Integer id=storeGoods.getStores().getStoreId();
            storegoodsService.updateGoodInfo(storeGoods);
            return "redirect:/Stores/ShowGoods/"+id; // 修改完成后重定向到首页即：列表页面（index）
    }

    @GetMapping("/brandgoods/{id}")
    public String index(Model model,@PathVariable("id") Integer store_id) {
        List<BrandRepos> repository =  brandReposRepository.findBrandReposByStatusIsNot("已入仓");
        model.addAttribute("Lists",  repository);
        return "Stores/brandgoods";
    }

        @PostMapping("/ShowGoods/{id}")    //搜索店铺商品
        public String search(Model model,@PathVariable("id") Integer store_id,String keyword) {
            if(brandReposService.findBrandReposByGoodName(keyword).isEmpty())
            {
                List<StoreGoods> goodlists=null;
                model.addAttribute("goods", goodlists);
                model.addAttribute("store_id", store_id);
            }                                //加个if是为了让用户在错误搜索的时候不跳到错误页面，而是显示空表。
            else {
                BrandRepos brandRepos=brandReposRepository.getBrandReposByGoodName(keyword);
                List<StoreGoods> goodlists = storegoodsService.findStoreGoodsByBrandRepos_GoodIdAndStores_StoreId(brandRepos.getGoodId(), store_id);
                model.addAttribute("goods", goodlists);
                model.addAttribute("store_id", store_id);
            }
            return "Stores/ShowGoods";
        }
    }

