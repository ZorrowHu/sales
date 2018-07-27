package com.platform.sales.surface;

import com.platform.sales.entity.Account;
import com.platform.sales.entity.Users;

public interface BrandAccountService {

    Account findByUserId(Integer id);

    Account update(Account account);

    void deleteByUserId(Users user);
}
