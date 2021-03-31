package com.platform.sales.controller;

import com.platform.sales.entity.OrderInfo;
import com.platform.sales.entity.StoreGoods;
import com.platform.sales.entity.Users;
import com.platform.sales.repository.BrandOrderRepository;
import com.platform.sales.repository.StoregoodsRepository;
import com.platform.sales.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("conrest")
public class ConsumerRestController {
        @Autowired
        BrandOrderRepository brandOrderRepository;
        @Autowired
        StoregoodsRepository storegoodsRepository;
        @Autowired
        UsersRepository usersRepository;

        @RequestMapping(value="/addcart",method= RequestMethod.POST)
        public Float addcart(@RequestParam("userId") Integer userId, @RequestParam("goodId") Integer storeGoodId){
                StoreGoods storeGoods = storegoodsRepository.findById(storeGoodId).get();
                Users consumer = usersRepository.findById(userId).get();
                //finding the one that relating with one goodId, userId and status
                OrderInfo order = brandOrderRepository.findOrderInfoByConsumer_UserIdAndGoods_GoodIdAndStatus(userId, storeGoods.getBrandRepos().getGoodId(), "待支付");

                if (order == null) {
                        OrderInfo newOrder = new OrderInfo();
                        //paytime and shipping is not confirmed here
                        newOrder.setStatus("待支付");
                        newOrder.setGoods(storeGoods.getBrandRepos());
                        newOrder.setConsumer(consumer);
                        newOrder.setStore(storeGoods.getStores());
                        newOrder.setQuantity(1);
                        newOrder.setTotalPrice(storeGoods.getPrice());
                        brandOrderRepository.save(newOrder);
                } else {
                        order.setOrderId(order.getOrderId());
                        order.setQuantity(order.getQuantity() + 1);
                        order.setTotalPrice(order.getTotalPrice() + storeGoods.getPrice());
                        brandOrderRepository.save(order);
                }

                List<OrderInfo> orders = brandOrderRepository.findAllByConsumer_UserIdAndStatus(userId, "待支付");
                Float totalPrice = new Float(0);
                for (OrderInfo each : orders){
                        totalPrice += each.getTotalPrice();
                }
                return  totalPrice;
        }
}
