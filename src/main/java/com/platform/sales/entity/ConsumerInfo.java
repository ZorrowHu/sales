package com.platform.sales.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ConsumerInfo {

    @Id
    @GeneratedValue
    private Integer id;     //主键ID，无实际意义
    @OneToOne
    private Users consumer; //消费者ID
    @OneToOne
    private Account account;//钱包ID

    public ConsumerInfo() {
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
