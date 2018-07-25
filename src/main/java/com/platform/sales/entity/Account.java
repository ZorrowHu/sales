package com.platform.sales.entity;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Account {

        @Id
        @GeneratedValue
        private Integer accountId;        //账户id
        //private Integer user_id
        @OneToOne                          //一个账户对应一个用户
        private Users user;                //用户id
        private Integer balance;           //余额
        private String payPwd;            //支付密码

        public Account() { }


        public Integer getAccountId() {
                return accountId;
        }

        public void setAccountId(Integer accountId) {
                this.accountId = accountId;
        }

        public Users getUser() {
                return user;
        }

        public void setUser(Users user) {
                this.user = user;
        }

        public Integer getBalance() {
                return balance;
        }

        public void setBalance(Integer balance) {
                this.balance = balance;
        }

        public String getPayPwd() {
                return payPwd;
        }

        public void setPayPwd(String payPwd) {
                this.payPwd = payPwd;
        }
}
