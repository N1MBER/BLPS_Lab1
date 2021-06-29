package com.blps_lab1.demo.kafka_server.service;

import com.blps_lab1.demo.kafka_server.beans.Task;
import com.blps_lab1.demo.kafka_server.mail_service.MailService;
import com.blps_lab1.demo.kafka_server.repository.TaskRepository;
import com.blps_lab1.demo.main_server.DTO.ProductDTO;
import com.blps_lab1.demo.main_server.beans.Notification;
import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.beans.User;
import com.blps_lab1.demo.main_server.repository.NotificationRepository;
import com.blps_lab1.demo.main_server.utils.DTOConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Profile("stats")
public class NotificationService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private MailService mailService;
//    @Autowired
//    private KafkaTemplate<String, String> responseTemplate;
//
//    public NotificationService(KafkaTemplate<String,String> kafkaTemplate){
//        this.responseTemplate = kafkaTemplate;
//    }

    public void completeTask(Task task) {
        System.out.println("Example");
        User user = task.getUser();
        List <Notification> notifications = notificationRepository.findAllByUserID(user.getID());
        List <Product> products = new ArrayList<>();
        List <ProductDTO> productDTOS = new ArrayList<>();
        for(Notification notification: notifications){
            if (notification.getProduct().getCount() > 0) {
                products.add(notification.getProduct());
                ProductDTO productDTO = dtoConverter.productDTOConvertor(notification.getProduct());
                productDTOS.add(productDTO);
            }
        }
        mailService.sendMailMessage(products, user.getID());
//        responseTemplate.send("kafka.tasks.response", task.getID() +" completed");
        taskRepository.delete(task);
    }

    public void createStats() {
        List<Task> list = taskRepository.findAll();
        for (Task task: list){
            completeTask(task);
        }
    }

}
