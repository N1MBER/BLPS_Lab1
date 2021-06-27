package com.blps_lab1.demo.main_server.DTO;

import com.blps_lab1.demo.main_server.DTO.ProductDTO;
import com.blps_lab1.demo.main_server.beans.StatusOrder;
import com.blps_lab1.demo.main_server.DTO.UserDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrderDTO implements Serializable {
    private Long ID;
    private UserDTO user;
    private List<ProductDTO> products;
    private String submit_date;
    private String update_date;
    private StatusOrder status;

    public List<ProductDTO> getProducts() {
        return products;
    }

    public String getSubmit_date() {
        return submit_date;
    }

    public String getUpdate_date() {
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

    public void setSubmit_date(String submit_date) {
        this.submit_date = submit_date;
    }

    public void setUpdate_date(String update_date) {
        this.update_date = update_date;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
