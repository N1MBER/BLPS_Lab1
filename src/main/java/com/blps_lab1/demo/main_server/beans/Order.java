package com.blps_lab1.demo.main_server.beans;

import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.beans.StatusOrder;
import com.blps_lab1.demo.main_server.beans.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="komus_order", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Order {
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
    private Date submit_date;
    @NotNull
    private Date update_date;
    @NotNull
    private StatusOrder status;

    public void setOrder(User user, ArrayList<Product> products, Date submit_date, Date update_date, StatusOrder status){
        this.status = status;
        this.user = user;
        this.submit_date = submit_date;
        this.update_date = update_date;
        this.products = products;
    }



    public List<Product> getProducts() {
        return products;
    }

    public Date getSubmit_date() {
        return submit_date;
    }

    public Date getUpdate_date() {
        return update_date;
    }

    public Long getId() {
        return ID;
    }

    public StatusOrder getStatus() {
        return status;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.ID = id;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setStatus(StatusOrder status) {
        this.status = status;
    }

    public void setSubmit_date(Date submit_date) {
        this.submit_date = submit_date;
    }

    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
