package com.blps_lab1.demo.DTO;

import java.io.Serializable;

public class ProductDTO implements Serializable {
    private Long Id;
    private String name;
    private Double price;
    private String brand;
    private Integer weight;
    private String country_contributor;
    private String size;
    private String description;
    private Integer count;

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

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

    public Integer getWeight() {
        return weight;
    }

    public String getBrand() {
        return brand;
    }

    public String getCountry_contributor() {
        return country_contributor;
    }

    public String getDescription() {
        return description;
    }

    public String getSize() {
        return size;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setCountry_contributor(String country_contributor) {
        this.country_contributor = country_contributor;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
