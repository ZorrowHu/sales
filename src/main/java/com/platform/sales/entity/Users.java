package com.platform.sales.entity;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Users {

        @Id
        @GeneratedValue
        private Integer user_id;             //用户id
        private String user_name;        //用户名
        private String password;          //密码
        private String user_role;           //用户角色

        public Users() { }

        public Integer getUser_id() { return this.user_id; }
        public void setUser_id(Integer user_id) { this.user_id = user_id; }
        public String getUser_name() { return this.user_name; }
        public void setUser_name(String user_name) { this.user_name = user_name;}
        public String getPassword() { return this.password; }
        public void setPassword(String password) { this.password = password;}
        public String getUser_role() { return this.user_role;}
        public void setUser_role(String user_role) { this.user_role = user_role; }
}
