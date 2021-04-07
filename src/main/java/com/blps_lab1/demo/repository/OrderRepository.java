package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.Order;
import com.blps_lab1.demo.beans.StatusOrder;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    ArrayList<Order> findAllByUserID(Long id);
    Order findByUserIDAndID(Long user_id, Long order_id);
    Order findByID(Long id);
}
