package com.blps_lab1.demo.repository;

import com.blps_lab1.demo.beans.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
