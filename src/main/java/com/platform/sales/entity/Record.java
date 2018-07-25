package com.platform.sales.entity;

import org.apache.catalina.User;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Record {

    @Id
    @GeneratedValue
    private Integer recordId;  //流水编号
    @ManyToOne
    private Users users;        //主ID
    @ManyToOne
    private Users op;           //客ID
    private Float money;        //金额
    private Date time;          //创建时间
    private String status;      //状态
    private String type;        //类型（转账、提现、充值）


    public Record() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
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
