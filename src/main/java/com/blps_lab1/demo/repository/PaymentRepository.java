package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.Payment;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface PaymentRepository extends CrudRepository<Payment, Long> {
    ArrayList<Payment> findAllByUserID(Long id);
    Payment findByUserIDAndID(Long user_id, Long payment_id);
}
