package com.blps_lab1.demo.main_server.beans;

import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.beans.User;
import com.blps_lab1.demo.main_server.beans.UserID;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name="komus_cart")
@IdClass(UserID.class)
public class CartItem {
    @Id
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id", referencedColumnName="id")
    private Product product;
    @NotNull
    private Integer count;

    public void setCartItem(Product new_product, User subscriber, Integer count){
        this.product = new_product;
        this.user = subscriber;
        this.count = count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public User getUser() {
        return user;
    }
}
