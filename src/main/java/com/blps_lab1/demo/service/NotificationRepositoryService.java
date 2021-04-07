package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.Notification;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.exceptions.UserNotFoundException;
import com.blps_lab1.demo.repository.NotificationRepository;
import com.blps_lab1.demo.service.DTOConverter;
import com.blps_lab1.demo.service.ProductRepositoryService;
import com.blps_lab1.demo.service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;

@Service
public class NotificationRepositoryService {

    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private User user;
    @Autowired
    private Product product;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private ProductRepositoryService productRepositoryService;

    public NotificationRepository getNotificationRepository() {
        return notificationRepository;
    }

    public void setNotificationRepository(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public void save(Notification notification){
        this.notificationRepository.save(notification);
    }

    public ResponseEntity getAllNotificationForUser(Long id){
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Notification> products_ids;
        products_ids = this.findAllByUserID(id);
        Iterator iterator = products_ids.iterator();
        Notification notification;
        while (iterator.hasNext()){
            notification = (Notification) iterator.next();
            try {
                if (notification.getProduct().getCount() != 0)
                    products.add(productRepositoryService.findByID(notification.getProduct().getID()));
            }catch (ProductNotFoundException e){

            }
        }
        ArrayList<ProductDTO> productDTOS = new ArrayList<>();
        for (Product product: products){
            productDTOS.add(dtoConverter.productDTOConvertor(product));
        }
        return new ResponseEntity(productDTOS, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> addToNotification(User user, Product product){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (product.getID() == null ){
            responseMessageDTO.setMessage("Product does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        if (user.getID() == null ){
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        Notification notification = new Notification();
        notification.setNotification(product, user);
        try {
            this.save(notification);
            responseMessageDTO.setMessage("Added to notification list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("You already have this product in notification list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseMessageDTO> deleteFromNotifications(User user, Product product){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (product.getID() == null ){
            responseMessageDTO.setMessage("Product does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        if (user.getID() == null ) {
            responseMessageDTO.setMessage("User does not exist");
        }
        try {
            this.notificationRepository.delete(this.findByUserIDAndProductID(user.getID(), product.getID()));
            responseMessageDTO.setMessage("Successfully deleted");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.ACCEPTED);
        } catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("There are no such product");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ArrayList<Notification> findAllByUserID(Long id){
        ArrayList<Notification> arrayList = this.notificationRepository.findAllByUserID(id);
        return arrayList;
    }

    public Notification findByUserIDAndProductID(Long user_id, Long product_id){
        Notification notification = this.notificationRepository.findByUserIDAndProductID(user_id, product_id);
        return notification;
    }
}
