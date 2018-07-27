package com.platform.sales.surface;

import com.platform.sales.entity.BrandRepos;

import java.util.List;

public interface BrandReposService {
    List<BrandRepos> findBrandReposByGoodName(String name);
    //根据商品id查找商品
    BrandRepos findByGoodId(Integer id);
}
