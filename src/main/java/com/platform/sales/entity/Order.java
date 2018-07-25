package com.platform.sales.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private Integer orderId;   //订单编号
    @ManyToOne
    private BrandRepos goods;  //商品ID
    @ManyToOne
    private Users seller;       //借买方ID
    @ManyToOne
    private Users consumer;     //消费者ID
    private Integer quantity;   //数量

    private Float totalPrice;  //此订单总额
    private Date payTime;      //支付时间

    public Order() {
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

    public Users getSeller() {
        return seller;
    }

    public void setSeller(Users seller) {
        this.seller = seller;
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
}
