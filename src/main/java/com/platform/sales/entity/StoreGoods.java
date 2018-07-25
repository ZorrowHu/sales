package com.platform.sales.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
public class StoreGoods {
    @Id
    @GeneratedValue
    private Integer store_goods_id;//无实际意义
    private float price;//借卖方商品售价
    @ManyToOne
    private Stores stores;//商店ID

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStore_goods_id() {
        return store_goods_id;
    }

    public void setStore_goods_id(int store_goods_id) {
        this.store_goods_id = store_goods_id;
    }
}
