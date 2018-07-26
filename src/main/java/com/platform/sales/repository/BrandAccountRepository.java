package com.platform.sales.repository;

import com.platform.sales.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandAccountRepository extends JpaRepository<Account, Integer> {

    //根据userId查找钱包
    Account findAccountByUserUserId(Integer id);

    //根据userId删除记录
    void deleteAccountByAccountId(Integer id);
}