package com.platform.sales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreorderRepository extends JpaRepository<StoreOrder,Integer> {
    //根据借卖方的id查询所有订单
    List<StoreOrder> findAllBySeller_UserId(Integer id);
}
