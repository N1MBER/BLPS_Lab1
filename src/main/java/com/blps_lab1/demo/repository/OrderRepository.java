package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface OrderRepository extends CrudRepository<Order, Long> {
    ArrayList<Order> findAllByUserID(Long id);
    Order findByUserIDAndID(Long user_id, Long order_id);
    Order findByID(Long id);
}
