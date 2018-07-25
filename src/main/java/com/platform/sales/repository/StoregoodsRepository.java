package com.platform.sales.repository;

import com.platform.sales.entity.StoreGoods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoregoodsRepository extends JpaRepository<StoreGoods,Integer> {
}
