package com.platform.sales.repository;

import com.platform.sales.entity.Brand_repos;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Brand_reposRepository extends JpaRepository<Brand_repos, Integer> {
        @Override
        Page<Brand_repos> findAll(Pageable pageable);

        List<Brand_repos> findBrand_reposByGoodName(String name);
        List<Brand_repos> findBrand_reposByStatusNot(String status);
        List<Brand_repos> findBrand_reposByGoodNameAndStatusNot(String name, String status);
}
