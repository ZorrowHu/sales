package com.platform.sales.surface;
import com.platform.sales.entity.Stores;
import com.platform.sales.repository.StoregoodsRepository;
import com.platform.sales.repository.StoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.platform.sales.entity.StoreGoods;

import java.util.List;

@Service
public class StoregoodsServiceImpl implements StoregoodsService{
    @Autowired
    private StoregoodsRepository storegoodsRepository;


    @Override
    public List<StoreGoods> getAllGoods() {
        return storegoodsRepository.findAll();
    }

    @Override
    public StoreGoods getGoodsByID(Integer GoodID) {
        return storegoodsRepository.findById(GoodID).get();
    }

    @Override
    public StoreGoods addGoodsInfo(StoreGoods goods) {
        return storegoodsRepository.save(goods);
    }

    @Override
    public void deleteByGoodID(Integer GoodID) {
    storegoodsRepository.deleteById(GoodID);
    }


    @Override
    public StoreGoods updateGoodInfo(StoreGoods goods) {
        return storegoodsRepository.save(goods);
    }

    @Override
    public List<StoreGoods> findAllByStores_StoreId(Integer id) {
        return storegoodsRepository.findAllByStores_StoreId(id);
    }


}

