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

        public Integer getUser_id() { return this.userId; }
        public void setUser_id(Integer user_id) { this.userId = user_id; }
        public String getUser_name() { return this.userName; }
        public void setUser_name(String user_name) { this.userName = user_name;}
        public String getPassword() { return this.password; }
        public void setPassword(String password) { this.password = password;}
        public String getUser_role() { return this.userRole;}
        public void setUser_role(String user_role) { this.userRole = user_role; }
}
