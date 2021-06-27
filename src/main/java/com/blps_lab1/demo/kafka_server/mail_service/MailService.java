package com.blps_lab1.demo.kafka_server.mail_service;

import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.List;

@Service
//@Profile("stats")
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    public void sendMailMessage(List<Product> products, Long id) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("n1mberspb33@gmail.com");
        message.setTo(this.userRepository.findByID(id).getEmail());
        message.setSubject("Товары снова в наличии");
        StringBuilder text_message = new StringBuilder("Товары: ");
        for (Product product: products){
            text_message.append(product.getName()).append(", ");
        }
        text_message.append("появились в продаже.");
        message.setText(text_message.toString());
        javaMailSender.send(message);
    }
}
