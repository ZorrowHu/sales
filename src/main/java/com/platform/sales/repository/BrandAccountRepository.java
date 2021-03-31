package com.platform.sales.repository;

import com.platform.sales.entity.Account;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface BrandAccountRepository extends JpaRepository<Account, Integer> {

    //根据userId查找钱包
    Account findAccountByUserUserId(Integer id);

    //根据userId删除记录
    @Transactional
    void deleteByUser(Users user);
}