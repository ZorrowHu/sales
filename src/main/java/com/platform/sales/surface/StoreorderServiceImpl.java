package com.platform.sales.surface;

import com.platform.sales.repository.StoreorderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class StoreorderServiceImpl implements StoreorderService {
    @Autowired
    StoreorderRepository storeorderRepository;
    //根据借卖方的所有id查找所有订单
    @Override
    public List<StoreOrder> findAllBySeller_UserId(Integer id) {
        return storeorderRepository.findAllBySeller_UserId(id);
    }
}
