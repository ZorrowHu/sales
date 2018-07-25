package com.platform.sales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class BrandInfo {
    @Id
    @GeneratedValue
    private Integer id;             //主键，无实际意义
    private String brName;         //品牌商名称
    private String brDescription;  //简介
    @OneToOne
    private Users users;            //一个品牌对应一个Users
    @OneToOne
    private Account account;        //一个品牌对应一个钱包
    private String image;           //商标

    public BrandInfo() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrName() {
        return brName;
    }

    public void setBrName(String brName) {
        this.brName = brName;
    }

    public String getBrDescription() {
        return brDescription;
    }

    public void setBrDescription(String brDescription) {
        this.brDescription = brDescription;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
