package com.platform.sales.entity;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Type {

        @Id
        @GeneratedValue
        private Integer type_id;       //类型id
        private String content_1;     //一级菜单
        private String content_2;     //二级菜单
        private String content_3;     //三级菜单

    public Type() {
    }

    public Integer getType_id() {
        return type_id;
    }

    public void setType_id(Integer type_id) {
        this.type_id = type_id;
    }

    public String getContent_1() {
        return content_1;
    }

    public void setContent_1(String content_1) {
        this.content_1 = content_1;
    }

    public String getContent_2() {
        return content_2;
    }

    public void setContent_2(String content_2) {
        this.content_2 = content_2;
    }

    public String getContent_3() {
        return content_3;
    }

    public void setContent_3(String content_3) {
        this.content_3 = content_3;
    }
}
