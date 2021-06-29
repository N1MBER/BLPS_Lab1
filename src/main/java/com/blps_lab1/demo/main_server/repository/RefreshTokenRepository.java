package com.blps_lab1.demo.main_server.repository;

import com.blps_lab1.demo.main_server.beans.RefreshToken;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
@Profile("dev")
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
