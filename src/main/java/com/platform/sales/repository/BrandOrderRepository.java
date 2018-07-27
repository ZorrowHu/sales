package com.platform.sales.repository;

import com.platform.sales.entity.BrandRepos;
import com.platform.sales.entity.OrderInfo;
import com.platform.sales.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
//备份成功
@Repository
public interface BrandOrderRepository extends JpaRepository<OrderInfo,Integer> {

    List<OrderInfo> findAllByGoods(BrandRepos good);

    List<OrderInfo> findAllByStatusAndGoods(String status, BrandRepos good);

    OrderInfo findByOrderId(Integer id);

    //OrderInfo findByGoodsGoodIdAndConsumer_UserIdAndStatus(Integer storeGoodId, Integer userId, String status);
    OrderInfo findOrderInfoByConsumer_UserIdAndGoods_GoodIdAndStatus(Integer storeGoodId, Integer userId, String status);
    List<OrderInfo> findAllByConsumer_UserIdAndStatus(Integer userId, String status);

    // 根据品牌商删除所有有关商品
    @Transactional
    void deleteAllByGoods_Brand(Users user);

    // 根据userid查找所有的订单
    List<OrderInfo> findAllByStoreUserUserId(Integer id);
    //根据借卖方的id查找用户自己的所有订单
    List<OrderInfo> findAllByStore_User_UserId(Integer id);
    //根据状态查找订单
    List<OrderInfo> findAllByStatus(String status);
    //根据店铺的id查找所有的订单
    List<OrderInfo> findAllByStore_StoreId(Integer id);
    //根据订单状态和店铺id查找订单
    List<OrderInfo> findAllByStatusAndStore_StoreId(String status,Integer id);
    //根据订单ID获得订单
    // 根据消费者id查询所有的订单
    List<OrderInfo> findAllByConsumer_UserId(Integer id);
}
