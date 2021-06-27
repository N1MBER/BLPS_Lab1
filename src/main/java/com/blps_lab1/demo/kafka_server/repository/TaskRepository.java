package com.blps_lab1.demo.kafka_server.repository;

import com.blps_lab1.demo.kafka_server.beans.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
