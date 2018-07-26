package com.platform.sales.surface;

import com.platform.sales.entity.BrandInfo;
import com.platform.sales.repository.BrandInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandInfoServiceImpl implements BrandInfoService{

    @Autowired
    private BrandInfoRepository brandInfoRepository;

    @Override
    public BrandInfo findByUserId(Integer id) {
        return brandInfoRepository.findByUsersUserId(id);
    }

    @Override
    public BrandInfo update(BrandInfo brandInfo) {
        return brandInfoRepository.save(brandInfo);
    }

    @Override
    public void deleteByUserId(Integer id) {
        brandInfoRepository.deleteBrandInfoByUsersUserId(id);
    }
}
