package com.platform.sales.surface;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.OrderInfo;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandOrderServiceImpl implements BrandOrderService {
    @Autowired
    private BrandOrderRepository brandOrderRepository;

    @Override
    public List<OrderInfo> findByGoods(BrandRepos goods) {
        return brandOrderRepository.findAllByGoods(goods);
    }

    @Override
    public List<OrderInfo> findByStatusAndGoods(String status, BrandRepos goods) {
        return brandOrderRepository.findAllByStatusAndGoods(status,goods);
    }

    @Override
    public OrderInfo findByOrderId(Integer id) {
        return brandOrderRepository.findByOrderId(id);
    }

    @Override
    public OrderInfo update(OrderInfo orderInfo) {
        return brandOrderRepository.save(orderInfo);
    }

    @Override
    public void delByConsumerOrSeller(Integer id_1, Integer id_2) {
        brandOrderRepository.deleteAllByConsumerUserIdOrSellerUserId(id_1,id_2);
    }


}
