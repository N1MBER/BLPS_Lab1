package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
