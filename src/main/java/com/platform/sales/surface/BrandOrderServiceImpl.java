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
    public List<OrderInfo> findAllByStore_User_UserIdOrderByPayTime(Integer id) {
        return brandOrderRepository.findAllByStore_User_UserIdOrderByPayTime(id);
    }

    @Override
    public List<OrderInfo> findAllByStatusOrderByPayTime(String status) {
        return brandOrderRepository.findAllByStatus(status);
    }

    @Override
    public List<OrderInfo> findAllByStore_StoreIdOrderByPayTime(Integer id) {
        return brandOrderRepository.findAllByStore_StoreIdOrderByPayTime(id);
    }

    @Override
    public List<OrderInfo> findAllByStatusAndStore_StoreIdOrderByPayTime(String status, Integer id) {
        return brandOrderRepository.findAllByStatusAndStore_StoreIdOrderByPayTime(status,id);
    }
}
