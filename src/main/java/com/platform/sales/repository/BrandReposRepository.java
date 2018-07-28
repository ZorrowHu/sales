package com.platform.sales.repository;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.Type;
import com.platform.sales.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface BrandReposRepository extends JpaRepository<BrandRepos, Integer> {
        @Override
        Page<BrandRepos> findAll(Pageable pageable);

        BrandRepos findByGoodIdAndBrand(Integer id, Users user);
        List<BrandRepos> findAllByBrand(Users user);
        List<BrandRepos> findBrandreposByGoodNameAndBrand(String name, Users user);
        List<BrandRepos> findBrandreposByStatusNotAndBrand(String status, Users user);
        List<BrandRepos> findBrandreposByGoodNameAndStatusNotAndBrand(String name, String status, Users user);
        List<BrandRepos> findBrandreposByTypeAndStatusAndBrand(Type type, String status, Users user);
        List<BrandRepos> findBrandReposByStatusIsNot(String status);
        List<BrandRepos> findBrandReposByGoodName(String name);
        List<BrandRepos> findBrandReposByStatusAndStatusAndGoodName(String sta1,String sta2,String name);
        List<BrandRepos> findBrandReposByStatusOrStatusAndGoodName(String s1,String s2,String name);
        @Transactional
        List<BrandRepos> findAllByType(Type type);
        BrandRepos getBrandReposByGoodName(String name);
        BrandRepos getBrandReposByGoodId(Integer id);
        @Transactional
        void deleteByGoodIdAndBrand(Integer id, Users user);
        @Transactional
        void deleteAllByBrand(Users user);
        //通过商品id查找记录
        BrandRepos findByGoodId(Integer id);
}
