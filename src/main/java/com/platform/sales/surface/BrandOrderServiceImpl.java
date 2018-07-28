package com.platform.sales.surface;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.OrderInfo;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//备份成功
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
    public List<OrderInfo> findAllByStore_User_UserId(Integer id) {
        return brandOrderRepository.findAllByStore_User_UserId(id);
    }

    @Override
    public List<OrderInfo> findAllByStatus(String status) {
        return brandOrderRepository.findAllByStatus(status);
    }

    @Override
    public List<OrderInfo> findAllByStore_StoreId(Integer id) {
        return brandOrderRepository.findAllByStore_StoreId(id);
    }

    @Override
    public List<OrderInfo> findAllByStatusAndStore_StoreId(String status, Integer id) {
        return brandOrderRepository.findAllByStatusAndStore_StoreId(status,id);
    }
}
