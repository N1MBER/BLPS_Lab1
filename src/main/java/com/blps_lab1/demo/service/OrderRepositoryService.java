package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.OrderDTO;
import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.Order;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.StatusOrder;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderRepositoryService {
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private User user;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private ProductRepositoryService productRepositoryService;

    public void save(Order order){
        this.orderRepository.save(order);
    }

    public ResponseEntity<ResponseMessageDTO> getAllOrderForUser(Long id){
        ArrayList <Order> orders = this.findAllByUserID(id);
        ArrayList <OrderDTO> orderDTOS = new ArrayList<>();
        for (Order order: orders){
            orderDTOS.add(dtoConverter.orderDTOConvertor(order));
        }
        return new ResponseEntity(orderDTOS, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> addOrder(User user, List<ProductDTO> products){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (user.getID() == null ){
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        Order order = new Order();
        order.setUpdate_date(new Date());
        order.setSubmit_date(new Date());
        ArrayList<Product> productArrayList = new ArrayList<>();
        for (ProductDTO productDTO: products){
            productArrayList.add(dtoConverter.productFromDTOConvertor(productDTO));
        }
        order.setOrder(user,productArrayList,new Date(),new Date(),StatusOrder.ADDED);
        order.setId(Integer.toUnsignedLong(order.hashCode()));
        try {
            this.save(order);
            responseMessageDTO.setMessage("Added to cart list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.CREATED);
        }  catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("You already have this order in list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<ResponseMessageDTO> updateStatus(Long id, StatusOrder status){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (id == null){
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        try {
            Order order = this.orderRepository.findByID(id);
            order.setStatus(status);
            order.setUpdate_date(new Date());
            this.orderRepository.save(order);
            responseMessageDTO.setMessage("Status is updated");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.OK);
        }catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("Ooops we have problem");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ArrayList<Order> findAllByUserID(Long id){
        return this.orderRepository.findAllByUserID(id);
    }

    public Order findByUserIDAndOrderId(Long user_id, Long order_id){
        return this.orderRepository.findByUserIDAndID(user_id, order_id);
    }


    public User getUser() {
        return user;
    }

    public DTOConverter getDtoConverter() {
        return dtoConverter;
    }

    public OrderRepository getOrderRepository() {
        return orderRepository;
    }

    public ProductRepositoryService getProductRepositoryService() {
        return productRepositoryService;
    }

    public UserRepositoryService getUserRepositoryService() {
        return userRepositoryService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void setDtoConverter(DTOConverter dtoConverter) {
        this.dtoConverter = dtoConverter;
    }

    public void setProductRepositoryService(ProductRepositoryService productRepositoryService) {
        this.productRepositoryService = productRepositoryService;
    }

    public void setUserRepositoryService(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }
}
