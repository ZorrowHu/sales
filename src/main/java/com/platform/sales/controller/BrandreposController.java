package com.platform.sales.controller;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandReposRepository;
import com.platform.sales.repository.TypeRepository;
import com.platform.sales.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
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
        @Autowired
        UsersRepository usersRepository;

        @GetMapping("/index")
        public String index(Model model, HttpSession session) {
                Users users = (Users) session.getAttribute("user");
                //user is the actual complete model of the current user
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                List<BrandRepos> repository =  brandReposRepository.findAllByBrand(user);
                model.addAttribute("Lists",  repository);
                return "brand/index";
        }

        @PostMapping("/index")
        public String index(String keyword, Model model, HttpSession session) {
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                List<BrandRepos> repository =  brandReposRepository.findBrandreposByGoodNameAndBrand(keyword, user);
                model.addAttribute("Lists",  repository);
                model.addAttribute("Message", "关键字搜索");
                return "brand/index";
        }

        @GetMapping("/newgoods")
        public String newgoods(Model model){
                List<String> primaries = typeRepository.getPrimary();
                model.addAttribute("primaries", primaries);

                return "brand/newgoods";
        }


        //@PostMapping("/addgoods")
        @RequestMapping(value="/addgoods", method=RequestMethod.POST)
        public String addgoods(BrandRepos brandrepos,
                               @RequestParam("type_primary") String primary,
                               @RequestParam("type_secondary") String secondary,
                               @RequestParam("type_tertiary") String tertiary,
                               @RequestParam("filamge") MultipartFile file,
                                HttpSession session
                                 ){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());

                brandrepos.setBrand(user);            //default user_id
                brandrepos.setStatus("新入仓");       //default status

                Type type = typeRepository.findTypeByContent1AndContent2AndContent3(primary, secondary, tertiary);
                brandrepos.setType(type);

                if (!file.isEmpty()) {
                        try {
                                String pathName = "src/main/resources/static/goodsimg/";
                                Long stamp = new Date().getTime();
                                String prefix = stamp.toString();
                                String fileName = prefix + file.getOriginalFilename();

                                brandrepos.setImage(fileName);

                                BufferedOutputStream out = new BufferedOutputStream(
                                        new FileOutputStream(new File(pathName + fileName)));
                                out.write(file.getBytes());
                                out.flush();
                                out.close();
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                //return "上传失败," + e.getMessage();
                        } catch (IOException e) {
                                e.printStackTrace();
                                //return "上传失败," + e.getMessage();
                        }
                        //return "上传成功";
                } else {
                        //return "上传失败，因为文件是空的.";
                }

                brandReposRepository.save(brandrepos);
                return "redirect:/brand/index";
        }

        @GetMapping("/delgoods/{id}")
        public String delgoods(@PathVariable("id") Integer id, HttpSession session){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                brandReposRepository.deleteByGoodIdAndBrand(id, user);
                return "redirect:/brand/index";
        }

        @GetMapping("/uptgoods/{id}")
        public String uptgoods(@PathVariable("id") Integer id, Model model, HttpSession session){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                BrandRepos good = brandReposRepository.findByGoodIdAndBrand(id, user);
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
                               @RequestParam("type_tertiary") String tertiary,
                               @RequestParam("filamge") MultipartFile file,
                               HttpSession session
                               ){
                good.setGoodId(id);
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());

                BrandRepos temp = brandReposRepository.findByGoodIdAndBrand(id, user);
                Type type = typeRepository.findTypeByContent1AndContent2AndContent3(primary,secondary,tertiary);
                good.setType(type);
                good.setStatus(temp.getStatus());
                good.setBrand(temp.getBrand());
                if (!file.isEmpty()) {
                        try {
                                String pathName = "src/main/resources/static/goodsimg/";
                                Long stamp = new Date().getTime();
                                String prefix = stamp.toString();
                                String fileName = prefix + file.getOriginalFilename();

                                good.setImage(fileName);

                                String existFileName = temp.getImage();
                                File existFile = new File(pathName + existFileName);
                                existFile.delete();
                                BufferedOutputStream out = new BufferedOutputStream(
                                        new FileOutputStream(new File(pathName + fileName)));
                                out.write(file.getBytes());
                                out.flush();
                                out.close();
                        } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                //return "上传失败," + e.getMessage();
                        } catch (IOException e) {
                                e.printStackTrace();
                                //return "上传失败," + e.getMessage();
                        }
                        //return "上传成功";
                } else {
                        //return "上传失败，因为文件是空的.";
                }
                brandReposRepository.save(good);
                return "redirect:/brand/index";
        }

        @GetMapping("/mainframe")
        public String mainframe(Model model, HttpSession session) {
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                List<BrandRepos> Lists = brandReposRepository.findBrandreposByStatusNotAndBrand("新入仓", user);
                model.addAttribute("Lists", Lists);
                return "brand/mainframe";
        }
        @PostMapping("/mainframe")
        public String mainframe(String keyword, Model model, HttpSession session) {
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                //find out all goods that is not newly added tot the repository
                List<BrandRepos> Lists = brandReposRepository.findBrandreposByGoodNameAndStatusNotAndBrand(keyword, "新入仓", user);

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
        public String newframe(Model model, HttpSession session){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                List<BrandRepos> Lists = brandReposRepository.findBrandreposByStatusNotAndBrand("新入仓", user);
                List<String> primaries = typeRepository.getPrimary();
                model.addAttribute("primaries", primaries);
                return "brand/newframe";
        }

        @RequestMapping(value="/addframe", method=RequestMethod.POST)
        public String addframe(@RequestParam("goodId") Integer goodId, HttpSession session){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                BrandRepos good = brandReposRepository.findByGoodIdAndBrand(goodId, user);
                good.setStatus("已入仓");
                brandReposRepository.save(good);
                return "redirect:/brand/mainframe";
        }

        @GetMapping("/loadgoods/{id}")
        public String loadgoods(@PathVariable("id") Integer id, HttpSession session){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                BrandRepos good = brandReposRepository.findByGoodIdAndBrand(id, user);
                good.setStatus("已上架");
                brandReposRepository.save(good);
                return "redirect:/brand/mainframe";
        }
        @GetMapping("/unloadgoods/{id}")
        public String unloadgoods(@PathVariable("id") Integer id, HttpSession session){
                Users users = (Users) session.getAttribute("user");
                Users user = usersRepository.findByUserNameAndUserRole(users.getUserName(),users.getUserRole());
                BrandRepos good = brandReposRepository.findByGoodIdAndBrand(id, user);
                good.setStatus("已下架");
                brandReposRepository.save(good);
                return "redirect:/brand/mainframe";
        }
}
