package com.platform.sales.entity;

import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Order {

    @Id
    @GeneratedValue
    private Integer order_id;   //订单编号
    @ManyToOne
    private Brand_repos goods;  //商品ID
    @ManyToOne
    private Users seller;       //借买方ID
    @ManyToOne
    private Users consumer;     //消费者ID
    private Integer quantity;   //数量

    private Float total_price;  //此订单总额
    private Date pay_time;      //支付时间

    public Order() {
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
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

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }

    public Brand_repos getGoods() {
        return goods;
    }

    public void setGoods(Brand_repos goods) {
        this.goods = goods;
    }

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }
}
