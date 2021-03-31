package com.platform.sales.surface;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.repository.BrandReposRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BrandReposServiceImpl implements BrandReposService{
    @Autowired
    private BrandReposRepository brandReposRepository;
    @Override
    public List<BrandRepos> findBrandReposByGoodName(String name) {
        return brandReposRepository.findBrandReposByGoodName(name);
    }

    @Override
    public BrandRepos findByGoodId(Integer id) {
        return brandReposRepository.findByGoodId(id);
    }

    @Override
    public BrandRepos update(BrandRepos brandRepos) {
        return brandReposRepository.save(brandRepos);
    }
}

