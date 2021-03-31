package com.platform.sales.surface;
import java.util.List;
import java.util.*;
import com.platform.sales.entity.StoreGoods;

public interface StoregoodsService {
    List<StoreGoods> getAllGoods();
    StoreGoods getGoodsByID(Integer GoodID);
    StoreGoods addGoodsInfo(StoreGoods goods);
    void deleteByGoodID(Integer GoodID);
    StoreGoods updateGoodInfo(StoreGoods goods);
    List<StoreGoods> findAllByStores_StoreId(Integer id);
    List<StoreGoods> findStoreGoodsByBrandRepos_GoodIdAndStores_StoreId(Integer gid,Integer sid);
}
