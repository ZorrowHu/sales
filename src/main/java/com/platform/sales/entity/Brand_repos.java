package com.platform.sales.entity;


import javax.persistence.*;

@Entity
public class Brand_repos {
    @Id
    @GeneratedValue
    private Integer goodId;    //商品ID
    @ManyToOne
    private Users brand;        //品牌商ID
    @ManyToOne()
    private Type type;          //类型ID
    private Float price;        //价格
    private Integer quantity;   //数量
    private String goodName;   //商品名
    private String status;      //状态
    private String image;       //图片


    public Brand_repos() { }

    public Integer getGoodId() { return goodId; }
    public void setGoodId(Integer goodId) { this.goodId = goodId; }
    public float getPrice() { return price; }
    public void setPrice(float price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getGoodName() { return goodName; }
    public void setGoodName(String goodName) { this.goodName = goodName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Users getBrand() {
        return brand;
    }

    public void setBrand(Users brand) {
        this.brand = brand;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
