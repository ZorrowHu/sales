package com.platform.sales.surface;

import com.platform.sales.entity.BrandInfo;

public interface BrandInfoService {

    BrandInfo findByUserId(Integer id);

    BrandInfo update(BrandInfo brandInfo);

    void deleteByUserId(Integer id);
}
