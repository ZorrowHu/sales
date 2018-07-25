package com.platform.sales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Brand_info {
    @Id
    @GeneratedValue
    private Integer id;             //主键，无实际意义
    private String br_name;         //品牌商名称
    private String br_description;  //简介
    @OneToOne
    private Users users;            //一个品牌对应一个Users
    @OneToOne
    private Account account;        //一个品牌对应一个钱包
    private String image;           //商标

    public Brand_info() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBr_name() {
        return br_name;
    }

    public void setBr_name(String br_name) {
        this.br_name = br_name;
    }

    public String getBr_description() {
        return br_description;
    }

    public void setBr_description(String br_description) {
        this.br_description = br_description;
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
