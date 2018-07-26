package com.platform.sales.repository;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandOrderRepository extends JpaRepository<OrderInfo,Integer> {

    List<OrderInfo> findAllByGoods(BrandRepos good);

    List<OrderInfo> findAllByStatusAndGoods(String status, BrandRepos good);
}
