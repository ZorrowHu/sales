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
import java.util.ArrayList;
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

        /**
         * 显示每个店铺各自的商品信息。
         * @param model   传送店铺商品表和店铺ID到前端
         * @param store_id 店铺ID
         * @return
         */
    @GetMapping("ShowGoods/{id}")
    public String getGoods(Model model,@PathVariable("id") Integer store_id){
            List<StoreGoods> goodlists=storegoodsService.findAllByStores_StoreId(store_id);
            model.addAttribute("goods",goodlists);
            model.addAttribute("store_id", store_id);
            return "Stores/ShowGoods";
    }

        /**
         * 为指定店铺添加商品的页面
         * @param store_id 店铺ID
         * @param model
         * @param good_id 品牌商的货物ID
         * @return
         */
    @GetMapping("/addgoods/{id}/{gid}")
    public String addGoodsindex(@PathVariable("id") Integer store_id,Model model,@PathVariable("gid") Integer good_id){
            model.addAttribute("id", store_id);
            return "Stores/addgoods";
    }

        /**
         * 接受前端传过来的信息，为指定的店铺添加指定的商品。
         * @param storeGoods
         * @param id
         * @return
         */

    @PostMapping("/addgoods")
    public String addgoods(StoreGoods storeGoods, Integer id){
            Stores stores=storesService.getStoreByID(id);
            storeGoods.setStores(stores);
            storegoodsService.addGoodsInfo(storeGoods);
            return "redirect:/Stores/ShowGoods/"+id;

    }

        /**
         * 删除店铺指定商品。
         * @param storegood_id 通过商品ID删除
         * @param id
         * @return
         */

    @GetMapping("deletegoods/{id}")
    public String deletegoods(@PathVariable("id") Integer storegood_id,Integer id){
            StoreGoods storeGoods=storegoodsService.getGoodsByID(storegood_id);
            id=storeGoods.getStores().getStoreId();
            storegoodsService.deleteByGoodID(storegood_id);
            return "redirect:/Stores/ShowGoods/"+id; // 删除完成后生定向到首页即：列表页面（index）
    }

        /**
         * 修改商店铺商品价格
         * @param storegoodId 通过商品ID修改
         * @param model
         * @return
         */

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

        /**
         * 展示每个店铺可以添加的商品，商品的信息表从品牌商那里获得。
         * @param model
         * @param store_id 根据不同的店铺ID可将商品添加到指定的店铺。
         * @return
         */

    @GetMapping("brandgoods/{id}")
    public String index(Model model,@PathVariable("id") Integer store_id) {
        Integer range;
        BrandRepos brandRepos;
        model.addAttribute("id",store_id);
        List<StoreGoods> storeGoods=storegoodsService.findAllByStores_StoreId(store_id);
        List<BrandRepos> test=new ArrayList<BrandRepos>();
        for(int i=0;i<storeGoods.size();i++)
        {
            range=storeGoods.get(i).getBrandRepos().getGoodId();
            brandRepos=brandReposRepository.getBrandReposByGoodId(range);
            test.add(brandRepos);
        }                        //为避免添加重复的商品，需要先得到本店铺的商品表，再根据这个表在拉取品牌商商品表时显示正确的信息。
        model.addAttribute("Test",test);
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

        @PostMapping("/brandgoods/{id}")
        public String searchbrand(Model model,@PathVariable("id") Integer store_id,String keyword) {
            List<BrandRepos> repository =  brandReposRepository.findBrandReposByGoodName(keyword);
            model.addAttribute("Lists",  repository);
            return "Stores/brandgoods";
        }
    }

