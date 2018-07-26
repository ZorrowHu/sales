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

        @Transactional
        void deleteByGoodIdAndBrand(Integer id, Users user);
        @Transactional
        void deleteAllByBrand(Users user);
}
