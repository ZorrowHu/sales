package com.platform.sales.entity;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Account {

        @Id
        @GeneratedValue
        private Integer account_id;        //账户id
        //private Integer user_id
        @OneToOne                               //一个账户对应一个用户
        private Users user;                      //用户id
        private Integer balance;             //余额
        private String pay_pwd;             //支付密码

        public Account() { }

        public Integer getAccount_id() { return account_id; }
        public void setAccount_id(Integer account_id) { this.account_id = account_id; }
        public Integer getBalance() { return balance; }
        public void setBalance(Integer balance) { this.balance = balance; }
        public Users getUser() { return user; }
        public void setUser(Users user) { this.user = user; }
        public String getPay_pwd() { return pay_pwd; }
        public void setPay_pwd(String pay_pwd) { this.pay_pwd = pay_pwd;
       }
}
