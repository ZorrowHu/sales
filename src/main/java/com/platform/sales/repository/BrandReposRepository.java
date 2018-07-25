package com.platform.sales.repository;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandReposRepository extends JpaRepository<BrandRepos, Integer> {
        @Override
        Page<BrandRepos> findAll(Pageable pageable);

        List<BrandRepos> findBrandreposByGoodName(String name);
        List<BrandRepos> findBrandreposByStatusNot(String status);
        List<BrandRepos> findBrandreposByGoodNameAndStatusNot(String name, String status);
        List<BrandRepos> findBrandreposByTypeAndStatus(Type type, String status);

}
