package com.platform.sales.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;

@Entity
public class StoreGoods {
    @Id
    @GeneratedValue
    private Integer storeGoodsId;//无实际意义
    private float price;//借卖方商品售价
    @ManyToOne
    private Stores stores;//商店ID


    public Integer getStoreGoodsId() {
        return storeGoodsId;
    }

    public void setStoreGoodsId(Integer storeGoodsId) {
        this.storeGoodsId = storeGoodsId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Stores getStores() {
        return stores;
    }

    public void setStores(Stores stores) {
        this.stores = stores;
    }
}
