package com.blps_lab1.demo.beans;

import java.io.Serializable;

public class UserID implements Serializable {

    private Long user;
    private Long product;

    public Long getProduct() {
        return product;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public void setProduct(Long product) {
        this.product = product;
    }
}
