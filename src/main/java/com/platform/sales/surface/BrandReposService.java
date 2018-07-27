package com.platform.sales.surface;

import com.platform.sales.entity.BrandRepos;

import java.util.List;

public interface BrandReposService {
    List<BrandRepos> findBrandReposByGoodName(String name);
}
