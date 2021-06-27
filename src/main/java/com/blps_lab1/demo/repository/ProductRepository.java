package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    Product findByID(Long id);
    @Override
    List<Product> findAll();
}
