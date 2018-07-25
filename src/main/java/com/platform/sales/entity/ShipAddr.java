package com.platform.sales.entity;

import javax.persistence.*;

@Entity
public class ShipAddr {

    @Id
    @GeneratedValue
    private Integer ship_id;    //地址表ID
    @ManyToOne
    private Users users;        //收货人ID
    private String receive_name;//收货人姓名
    private String province;    //省份
    private String city;        //城市
    private String district;    //区
    private String address;     //具体地址
    private String postal;      //邮编
    private String phone;       //电话
    private String email;       //邮箱

    public ShipAddr() {
    }

    public Integer getShip_id() {
        return ship_id;
    }

    public void setShip_id(Integer ship_id) {
        this.ship_id = ship_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public String getReceive_name() {
        return receive_name;
    }

    public void setReceive_name(String receive_name) {
        this.receive_name = receive_name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
