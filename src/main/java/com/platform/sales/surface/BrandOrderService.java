package com.platform.sales.surface;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.OrderInfo;
import com.platform.sales.entity.Users;
import java.util.List;
//备份成功
public interface BrandOrderService {

    List<OrderInfo> findByGoods(BrandRepos goods);

    List<OrderInfo> findByStatusAndGoods(String status, BrandRepos goods);

    OrderInfo findByOrderId(Integer id);

    OrderInfo update(OrderInfo orderInfo);
    //根据借卖方的id查找所有订单
    List<OrderInfo> findAllByStore_User_UserId(Integer id);
    //根据状态查找所有订单
    List<OrderInfo> findAllByStatus(String status);
    //根据店铺id查找所有订单
    List<OrderInfo> findAllByStore_StoreId(Integer id);
    //根据订单的状态和店铺的id查找所有订单
    List<OrderInfo> findAllByStatusAndStore_StoreId(String status,Integer id);
}