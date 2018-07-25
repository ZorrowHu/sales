package com.platform.sales.entity;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Users {

        @Id
        @GeneratedValue
        private Integer userId;             //用户id
        private String userName;        //用户名
        private String password;          //密码
        private String userRole;           //用户角色

        public Users() { }

        public Integer getUserId() {
                return userId;
        }

        public String getUserName() {
                return userName;
        }

        public String getPassword() {
                return password;
        }

        public String getUserRole() {
                return userRole;
        }

        public void setUserId(Integer userId) {
                this.userId = userId;
        }

        public void setUserName(String userName) {
                this.userName = userName;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public void setUserRole(String userRole) {
                this.userRole = userRole;
        }
}
