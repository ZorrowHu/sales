package com.platform.sales.repository;

import com.platform.sales.entity.BrandRepos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandReposRepository extends JpaRepository<BrandRepos, Integer> {
        @Override
        Page<BrandRepos> findAll(Pageable pageable);

        List<BrandRepos> findBrandReposByGoodName(String name);
}
