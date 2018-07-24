package com.platform.sales.entity;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Brandrepos {
        @Id
        @GeneratedValue
        private Integer good_id;
        private Integer user_id;
        private Integer type_id;
        private float price;
        private Integer quantity;
        private String good_name;
        private String status;
        private String image;


        public Brandrepos() { }

        public Integer getGood_id() { return good_id; }
        public void setGood_id(Integer good_id) { this.good_id = good_id; }
        public Integer getUser_id() { return user_id; }
        public void setUser_id(Integer user_id) { this.user_id = user_id; }
        public Integer getType_id() { return type_id; }
        public void setType_id(Integer type_id) { this.type_id = type_id; }
        public float getPrice() { return price; }
        public void setPrice(float price) { this.price = price; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getGood_name() { return good_name; }
        public void setGood_name(String good_name) { this.good_name = good_name; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getImage() { return image; }
        public void setImage(String image) { this.image = image; }

}
