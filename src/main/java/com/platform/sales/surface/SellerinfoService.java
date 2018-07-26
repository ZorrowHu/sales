package com.platform.sales.surface;

import com.platform.sales.entity.Account;
import com.platform.sales.entity.BrandInfo;
import com.platform.sales.entity.SellerInfo;
import com.platform.sales.entity.Users;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SellerinfoService {
    public SellerInfo updateSeller(SellerInfo seller_info);
    public List<SellerInfo> findById(Integer id);
    public List<Users> getUser(Integer id);
}
