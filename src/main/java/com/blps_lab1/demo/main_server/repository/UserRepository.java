package com.blps_lab1.demo.main_server.repository;

import com.blps_lab1.demo.main_server.beans.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    User findByID(Long id);
    ArrayList<User> findAll();
}
