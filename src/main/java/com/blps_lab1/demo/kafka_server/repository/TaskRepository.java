package com.blps_lab1.demo.kafka_server.repository;

import com.blps_lab1.demo.kafka_server.beans.Task;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
@Profile("stats")
public interface TaskRepository extends JpaRepository<Task, Long> {
}
