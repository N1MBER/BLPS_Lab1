package com.blps_lab1.demo.DTO;

import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.StatusOrder;
import com.blps_lab1.demo.beans.User;
import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDTO implements Serializable {
    private Long ID;
    private UserDTO user;
    private List<ProductDTO> products;
    private Date submit_date;
    private Date update_date;
    private StatusOrder status;

    public List<ProductDTO> getProducts() {
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

    public UserDTO getUser() {
        return user;
    }

    public void setId(Long id) {
        this.ID = id;
    }

    public void setProducts(ArrayList<ProductDTO> products) {
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

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
