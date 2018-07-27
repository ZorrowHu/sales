package com.platform.sales.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OrderInfo {

        @Id
        @GeneratedValue
        private Integer orderId;   //订单编号
        @ManyToOne
        private BrandRepos goods;  //商品ID
        @ManyToOne
        private Stores store;      //店铺ID
        @ManyToOne
        private Users consumer;     //消费者ID
        @ManyToOne
        private ShipAddr ship;      //收货地址

        private Integer quantity;   //数量
        private String status;      //类型()

        private Float totalPrice;  //此订单总额
        private Date payTime;      //支付时间

        public OrderInfo() {
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public BrandRepos getGoods() {
            return goods;
        }

        public void setGoods(BrandRepos goods) {
            this.goods = goods;
        }

        public Users getConsumer() {
            return consumer;
        }

        public void setConsumer(Users consumer) {
            this.consumer = consumer;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Float getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(Float totalPrice) {
            this.totalPrice = totalPrice;
        }

        public Date getPayTime() {
            return payTime;
        }

        public void setPayTime(Date payTime) {
            this.payTime = payTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public ShipAddr getShip() {
            return ship;
        }

        public void setShip(ShipAddr ship) {
            this.ship = ship;
        }

    public Stores getStore() {
        return store;
    }

    public void setStore(Stores store) {
        this.store = store;
    }
}
