package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.OrderDTO;
import com.blps_lab1.demo.DTO.PaymentDTO;
import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.*;
import com.blps_lab1.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaymentRepositoryService {
    @Autowired
    private DTOConverter dtoConverter;
    @Autowired
    private User user;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private ProductRepositoryService productRepositoryService;

    public void save(Payment payment){
        this.paymentRepository.save(payment);
    }

    public ResponseEntity<ResponseMessageDTO> getAllPaymentForUser(Long id){
        ArrayList<Payment> payments = this.findAllByUserID(id);
        ArrayList <PaymentDTO> paymentDTOS = new ArrayList<>();
        for (Payment payment: payments){
            paymentDTOS.add(dtoConverter.paymentDTOConvertor(payment));
        }
        return new ResponseEntity(paymentDTOS, HttpStatus.OK);
    }

    public ResponseEntity<ResponseMessageDTO> addPayment(User user, List<ProductDTO> products, PaymentType paymentType){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (user.getID() == null ){
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        Payment payment = new Payment();
        payment.setDate(new Date());
        ArrayList<Product> productArrayList = new ArrayList<>();
        float price = 0;
        for (ProductDTO productDTO: products){
            Product product = dtoConverter.productFromDTOConvertor(productDTO);
            productArrayList.add(product);
            price += product.getPrice();
        }
        payment.setPayment(user,productArrayList,new Date(),price, paymentType);
        payment.setID(Integer.toUnsignedLong(payment.hashCode()));
        try {
            this.save(payment);
            responseMessageDTO.setMessage("Added to payment list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.CREATED);
        }  catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("You already have this payment in list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }

    public ArrayList<Payment> findAllByUserID(Long id){
        return this.paymentRepository.findAllByUserID(id);
    }

    public PaymentDTO findByUserIDAndPaymentId(Long user_id, Long payment_id){
        Payment payment = this.paymentRepository.findByUserIDAndID(user_id, payment_id);
        return dtoConverter.paymentDTOConvertor(payment);
    }
}
