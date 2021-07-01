package com.blps_lab1.demo.main_server.repository;

import com.blps_lab1.demo.main_server.beans.Notification;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//@Profile("dev")
public interface NotificationRepository extends CrudRepository<Notification, Long> {
    ArrayList<Notification> findAllByUserID(Long id);
    Notification findByUserIDAndProductID(Long user_id, Long product_id);
}
