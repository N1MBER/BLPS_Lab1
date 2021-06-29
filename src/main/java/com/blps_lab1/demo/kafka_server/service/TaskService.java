package com.blps_lab1.demo.kafka_server.service;

import com.blps_lab1.demo.kafka_server.beans.Task;
import com.blps_lab1.demo.kafka_server.repository.TaskRepository;
import com.blps_lab1.demo.main_server.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableKafka
@Profile("stats")
public class TaskService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationService notificationService;
    Logger logger = LogManager.getLogger(TaskService.class);

    @KafkaListener(topics = "kafka.tasks.add")
    public void messageListener(ConsumerRecord<Long, String> record) throws JsonProcessingException{
        Task task = new ObjectMapper().readValue(record.value(), Task.class);
        System.out.println(task);
        notificationService.createStats();
        taskRepository.saveAndFlush(task);
    }

    public void saveTask(Task task){
        taskRepository.saveAndFlush(task);
    }

    public void deleteTask(long id){
        if (taskRepository.existsById(id)){
            taskRepository.deleteById(id);
        }
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }
}
