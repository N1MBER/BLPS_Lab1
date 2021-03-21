package com.blps_lab1.demo.DTO;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private Long Id;
    private String name;
    private Double price;

    public Long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        Id = id;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
