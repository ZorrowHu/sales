package com.platform.sales.surface;

import com.platform.sales.entity.Account;
import com.platform.sales.repository.BrandAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandAccountServiceImpl implements BrandAccountService{

    @Autowired
    private BrandAccountRepository brandAccountRepository;

    @Override
    public Account findByUserId(Integer id) {
        return brandAccountRepository.findAccountByUserUserId(id);
    }

    @Override
    public Account update(Account account) {
        return brandAccountRepository.save(account);
    }

    @Override
    public void deleteByUserId(Integer id) {
        brandAccountRepository.deleteAccountByAccountId(id);
    }
}
