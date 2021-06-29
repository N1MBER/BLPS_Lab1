package com.blps_lab1.demo.main_server.repository;

import com.blps_lab1.demo.main_server.beans.Payment;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    ArrayList<Payment> findAllByUserID(Long id);
    Payment findByUserIDAndID(Long user_id, Long payment_id);
}
