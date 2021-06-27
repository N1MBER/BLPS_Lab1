package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface NotificationRepository extends CrudRepository<Notification, Long> {
    ArrayList<Notification> findAllByUserID(Long id);
    Notification findByUserIDAndProductID(Long user_id, Long product_id);
}
