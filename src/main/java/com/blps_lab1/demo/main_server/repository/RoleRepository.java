package com.blps_lab1.demo.main_server.repository;

import com.blps_lab1.demo.main_server.beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
