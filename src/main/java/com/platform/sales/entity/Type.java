package com.platform.sales.entity;

import javax.annotation.Generated;
import javax.persistence.*;

@Entity
public class Type {

        @Id
        @GeneratedValue
        private Integer typeId;       //类型id
        private String content1;     //一级菜单
        private String content2;     //二级菜单
        private String content3;     //三级菜单

    public Type() {
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }

    public String getContent3() {
        return content3;
    }

    public void setContent3(String content3) {
        this.content3 = content3;
    }
}
