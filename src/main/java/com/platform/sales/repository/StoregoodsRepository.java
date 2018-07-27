package com.platform.sales.repository;

import com.platform.sales.entity.StoreGoods;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface StoregoodsRepository extends JpaRepository<StoreGoods,Integer> {
    List<StoreGoods> findAllByStores_StoreId(Integer id);

        @Query(value = "select s from StoreGoods s where s.brandRepos.goodName like %?1%")
        List<StoreGoods> getByGoodNameLike(String keyword);

        List<StoreGoods> findAllByBrandReposTypeTypeId(Integer id);
    List<StoreGoods> findStoreGoodsByBrandRepos_GoodIdAndStores_StoreId(Integer gid,Integer sid);

    // 通过用户删除所有的货物
    @Transactional
    void deleteAllByStores_User(Users user);
}
