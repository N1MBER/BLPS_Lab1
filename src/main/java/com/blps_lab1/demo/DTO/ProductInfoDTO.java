package com.blps_lab1.demo.DTO;

import java.io.Serializable;

public class ProductInfoDTO implements Serializable {
    private ProductDTO product;
    private String brand;
    private Integer weight;
    private String country_contributor;
    private String size;
    private String description;

    public String getSize() {
        return size;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry_contributor() {
        return country_contributor;
    }

    public String getBrand() {
        return brand;
    }

    public Integer getWeight() {
        return weight;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCountry_contributor(String country_contributor) {
        this.country_contributor = country_contributor;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

}
