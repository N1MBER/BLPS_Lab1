package com.blps_lab1.demo.XA;

import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.beans.Payment;
import com.blps_lab1.demo.beans.PaymentType;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.service.PaymentRepositoryService;
import com.blps_lab1.demo.service.ProductRepositoryService;
import com.blps_lab1.demo.utils.DTOConverter;
import com.blps_lab1.demo.validation.ValidationPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentAddXA {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PaymentRepositoryService paymentRepositoryService;

    @Autowired
    private ValidationPaymentService validationPaymentService;

    @Autowired
    private DTOConverter dtoConverter;

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @Transactional
    public ResponseEntity<ResponseMessageDTO> addPayment(User user, List<Long> products_ids, PaymentType paymentType){
        ResponseMessageDTO responseMessageDTO = new ResponseMessageDTO();
        if (user.getID() == null ){
            responseMessageDTO.setMessage("User does not exist");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        Payment payment = new Payment();
        payment.setDate(new Date());
        ArrayList<Product> productArrayList = new ArrayList<>();
        float price = 0;
        try {
            for (Long id: products_ids){
                Product product = productRepositoryService.findByID(id);
                productArrayList.add(product);
                price += product.getPrice();
            }
        }catch (ProductNotFoundException e){
            responseMessageDTO.setMessage("Product not found");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
        payment.setPayment(user,productArrayList,new Date(),price, paymentType);
        payment.setTotalPrice(price);
        payment.setID(Integer.toUnsignedLong(payment.hashCode()));
        try {
            this.paymentRepositoryService.save(payment);
            responseMessageDTO.setMessage("Added to payment list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.CREATED);
        }  catch (DataIntegrityViolationException e){
            responseMessageDTO.setMessage("You already have this payment in list");
            return new ResponseEntity<>(responseMessageDTO, HttpStatus.BAD_REQUEST);
        }
    }
}
