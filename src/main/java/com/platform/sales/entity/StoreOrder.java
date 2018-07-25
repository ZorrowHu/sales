package com.platform.sales.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class StoreOrder {

    @Id
    @GeneratedValue
    private Integer id;         //店铺订单ID
    @ManyToOne
    private Users consumer;     //消费者ID
    @ManyToOne
    private Stores store;       //店铺ID
    private Date payTime;      //支付时间
    @ManyToOne
    private ShipAddr ship;     //收货地址
    private String status;      //订单状态
    private Float totalPrice;  //订单总价

    public StoreOrder() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Users getConsumer() {
        return consumer;
    }

    public void setConsumer(Users consumer) {
        this.consumer = consumer;
    }

    public Stores getStore() {
        return store;
    }

    public void setStore(Stores store) {
        this.store = store;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public ShipAddr getShip() {
        return ship;
    }

    public void setShip(ShipAddr ship) {
        this.ship = ship;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
