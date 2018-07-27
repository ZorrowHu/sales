package com.platform.sales.repository;

import com.platform.sales.entity.ShipAddr;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ShipAddrRepository extends JpaRepository<ShipAddr, Integer> {

    // 根据用户删除其所有收货地址信息
    @Transactional
    void deleteAllByUsers(Users user);
}
