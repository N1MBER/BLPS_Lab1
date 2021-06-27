package com.blps_lab1.demo.main_server.beans;

import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.beans.User;
import com.blps_lab1.demo.main_server.beans.UserID;

import javax.persistence.*;

@Entity
@Table(name="komus_notifications")
@IdClass(UserID.class)
public class Notification {
    @Id
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id", referencedColumnName="id")
    private Product product;

    public void setNotification(Product new_product, User subscriber){
        this.product = new_product;
        this.user = subscriber;
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
