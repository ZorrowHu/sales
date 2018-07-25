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
    private Date pay_time;      //支付时间
    @ManyToOne
    private ShipAddr ship;     //收货地址
    private String status;      //订单状态
    private Float total_price;  //订单总价

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

    public Date getPay_time() {
        return pay_time;
    }

    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
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

    public Float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Float total_price) {
        this.total_price = total_price;
    }
}
