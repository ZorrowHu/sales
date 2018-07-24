package com.platform.sales.entity;

import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Record {

    @Id
    @GeneratedValue
    private Integer record_id;  //流水编号
    @ManyToOne
    private Users users;        //主ID
    @ManyToOne
    private Users op;           //客ID
    private Float money;        //金额
    private Date time;          //创建时间
    private String status;      //状态


    public Record() {
    }

    public Integer getRecord_id() {
        return record_id;
    }

    public void setRecord_id(Integer record_id) {
        this.record_id = record_id;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Users getOp() {
        return op;
    }

    public void setOp(Users op) {
        this.op = op;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
