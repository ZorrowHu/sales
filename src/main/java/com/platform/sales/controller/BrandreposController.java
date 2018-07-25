package com.platform.sales.controller;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandReposRepository;
import com.platform.sales.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("brand")
public class BrandreposController {

        @Autowired
        BrandReposRepository brandReposRepository;
        @Autowired
        TypeRepository typeRepository;

        @GetMapping("/index")
        public String index(Model model) {
                List<BrandRepos> repository =  brandReposRepository.findAll();
                model.addAttribute("Lists",  repository);
                return "brand/index";
        }

        @PostMapping("/index")
        public String index(String keyword, Model model) {
                List<BrandRepos> repository =  brandReposRepository.findBrandreposByGoodName(keyword);
                model.addAttribute("Lists",  repository);
                model.addAttribute("Message", "关键字搜索");
                return "brand/index";
        }

        @GetMapping("/newgoods")
        public String newgoods(){
                return "brand/newgoods";
        }

        @PostMapping("/addgoods")
        public String addgoods(BrandRepos brandrepos){
                Users brand = new Users();
                brand.setUserId(1);
                brandrepos.setBrand(brand);            //default user_id
                brandrepos.setStatus("新入仓");       //default status

                Type type = typeRepository.findById(brandrepos.getType().getTypeId()).get();
                brandrepos.setType(type);

                brandReposRepository.save(brandrepos);
                return "redirect:/brand/index";
        }

        @GetMapping("/delgoods/{id}")
        public String delgoods(@PathVariable("id") Integer id){
                brandReposRepository.deleteById(id);
                return "redirect:/brand/index";
        }

        @GetMapping("/uptgoods/{id}")
        public String uptgoods(@PathVariable("id") Integer id, Model model){
                BrandRepos good = brandReposRepository.findById(id).get();
                List<String> primaries = typeRepository.getPrimary();
                List<String> secondaries = typeRepository.getSecondary(good.getType().getContent1());
                List<String> tertiaries = typeRepository.getTertiary(good.getType().getContent1(), good.getType().getContent2());
                model.addAttribute("good", good);
                model.addAttribute("primaries", primaries);
                model.addAttribute("secondaries", secondaries);
                model.addAttribute("tertiaries", tertiaries);
                return "brand/update";
        }
        //@RequestParam("goodId") Integer goodId
        //@PostMapping("/doupdate/{id}")
        @RequestMapping(value="/doupdate/{id}", method=RequestMethod.POST)
        public String doupdate(@PathVariable("id") Integer id, BrandRepos good,
                               @RequestParam("type_primary") String primary,
                               @RequestParam("type_secondary") String secondary,
                               @RequestParam("type_tertiary") String tertiary
                               ){
                good.setGoodId(id);

                Type type = typeRepository.findTypeByContent1AndContent2AndContent3(primary,secondary,tertiary);
                good.setType(type);

                brandReposRepository.save(good);
                return "redirect:/brand/index";
        }

        @GetMapping("/mainframe")
        public String mainframe(Model model) {
                List<BrandRepos> Lists = brandReposRepository.findBrandreposByStatusNot("新入仓");
                model.addAttribute("Lists", Lists);
                return "brand/mainframe";
        }
        @PostMapping("/mainframe")
        public String mainframe(String keyword, Model model) {
                //find out all goods that is not newly added tot the repository
                List<BrandRepos> Lists = brandReposRepository.findBrandreposByGoodNameAndStatusNot(keyword, "新入仓");
                Map<String,String> taskMap=new HashMap<String,String>();
                for (BrandRepos list : Lists){
                        //list.getType().getContent3();
                        //taskMap.put(list.getGoodId(), typeRepository.findById(list.getType().getTypeId()).get().getContent3());

                }
                model.addAttribute("Lists", Lists);
                return "brand/mainframe";
        }

        @GetMapping("/delframe/{id}")
        public String delframe(@PathVariable("id") Integer id){
                BrandRepos goods = brandReposRepository.findById(id).get();
                goods.setStatus("新入仓");
                brandReposRepository.save(goods);
                return "redirect:/brand/mainframe";
        }

        @GetMapping("/newframe")
        public String newframe(Model model){
                List<BrandRepos> Lists = brandReposRepository.findBrandreposByStatusNot("新入仓");
                List<String> primaries = typeRepository.getPrimary();
                model.addAttribute("primaries", primaries);
                return "brand/newframe";
        }

        @RequestMapping(value="/addframe", method=RequestMethod.POST)
        public String addframe(@RequestParam("goodId") Integer goodId){
                BrandRepos good = brandReposRepository.findById(goodId).get();
                good.setStatus("已入仓");
                brandReposRepository.save(good);
                return "redirect:/brand/mainframe";
        }

        @GetMapping("/loadgoods/{id}")
        public String loadgoods(@PathVariable("id") Integer id){
                BrandRepos good = brandReposRepository.findById(id).get();
                good.setStatus("已上架");
                brandReposRepository.save(good);
                return "redirect:/brand/mainframe";
        }
        @GetMapping("/unloadgoods/{id}")
        public String unloadgoods(@PathVariable("id") Integer id){
                BrandRepos good = brandReposRepository.findById(id).get();
                good.setStatus("已下架");
                brandReposRepository.save(good);
                return "redirect:/brand/mainframe";
        }
}
