package com.blps_lab1.demo.main_server.beans;

import com.blps_lab1.demo.main_server.beans.PaymentType;
import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.beans.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name="komus_payment", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Payment {
    @Id
    @GeneratedValue
    @Column(unique = true)
    private Long ID;

    @NotNull
    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    @NotNull
    @ManyToMany
    private List<Product> products;

    @NotNull
    private Date date;

    @NotNull
    private float totalPrice;

    @NotNull
    private PaymentType paymentType;

    public void setPayment (
            User user,
            ArrayList<Product> products,
            Date date,
            float price,
            PaymentType paymentType
    ) {
        this.date = date;
        this.user = user;
        this.products = products;
        this.paymentType = paymentType;
        this.totalPrice = price;
    }

    public Date getDate() {
        return date;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Long getID() {
        return ID;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public User getUser() {
        return user;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setUser(User user) {
        this.user = user;
    }
}