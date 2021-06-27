package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface CartRepository extends CrudRepository<CartItem, Long> {
    ArrayList<CartItem> findAllByUserID(Long id);
    Integer deleteAllByUserID(Long id);
    CartItem findByUserIDAndProductID(Long user_id, Long product_id);
}
