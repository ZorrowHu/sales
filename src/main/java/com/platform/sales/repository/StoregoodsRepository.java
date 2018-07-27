package com.platform.sales.repository;

import com.platform.sales.entity.StoreGoods;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoregoodsRepository extends JpaRepository<StoreGoods,Integer> {
    List<StoreGoods> findAllByStores_StoreId(Integer id);
    List<StoreGoods> findStoreGoodsByBrandRepos_GoodIdAndStores_StoreId(Integer gid,Integer sid);
}