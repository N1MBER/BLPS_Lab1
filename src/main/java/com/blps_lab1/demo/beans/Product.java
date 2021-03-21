package com.blps_lab1.demo.beans;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "komus_product",uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Product {
    @Id
    @GeneratedValue
    @Column(unique = true)
    private Long ID;
    @NotNull
    private String name;
    @NotNull
    private Double price;
    private String brand;
    private Integer weight;
    private String country_contributor;
    private String size;
    private String description;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(Long id) {
        this.ID = id;
    }

    @Id
    public Long getID() {
        return ID;
    }
}
