package com.platform.sales.surface;

import com.platform.sales.entity.Account;

public interface BrandAccountService {

    Account findByUserId(Integer id);

    Account update(Account account);

    void deleteByUserId(Integer id);
}
