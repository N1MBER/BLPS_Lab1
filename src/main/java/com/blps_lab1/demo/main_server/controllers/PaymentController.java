package com.blps_lab1.demo.main_server.controllers;

import com.blps_lab1.demo.main_server.DTO.PaymentDTO;
import com.blps_lab1.demo.main_server.DTO.PaymentElementDTO;
import com.blps_lab1.demo.main_server.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.main_server.XA.PaymentAddXA;
import com.blps_lab1.demo.main_server.beans.Payment;
import com.blps_lab1.demo.main_server.beans.User;
import com.blps_lab1.demo.main_server.exceptions.PaymentValidateException;
import com.blps_lab1.demo.main_server.exceptions.UserNotFoundException;
import com.blps_lab1.demo.main_server.utils.DTOConverter;
import com.blps_lab1.demo.main_server.service.PaymentRepositoryService;
import com.blps_lab1.demo.main_server.service.UserRepositoryService;
import com.blps_lab1.demo.main_server.validation.ValidationPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/payment")
@Api(value = "Payment api")
public class PaymentController {

    @Autowired
    private PaymentRepositoryService paymentRepositoryService;

    @Autowired
    private PaymentAddXA paymentAddXA;

    @Autowired
    private DTOConverter dtoConverter;

    @Autowired
    private UserRepositoryService userRepositoryService;

    private ResponseMessageDTO message;

    @Autowired
    private ValidationPaymentService validationPaymentService;

    @GetMapping("/")
    @ApiOperation(value = "Get list of payments")
    public ResponseEntity getAllPayments(HttpServletRequest request) {
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return this.paymentRepositoryService.getAllPaymentForUser(user.getID());
        } catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get current payment")
    public ResponseEntity getPaymentById(@PathVariable("id") Long id,HttpServletRequest request) {
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return new ResponseEntity<>(this.paymentRepositoryService.findByUserIDAndPaymentId(user.getID(), id), HttpStatus.OK);
        } catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Get current payment")
    public ResponseEntity getPayments(HttpServletRequest request) {
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            ArrayList<PaymentElementDTO> paymentDTOS = new ArrayList<>();
            ArrayList<Payment> payments = this.paymentRepositoryService.findAllByUserID(user.getID());
            for (Payment payment: payments){
                PaymentElementDTO paymentDTO = dtoConverter.paymentElementDTOConvertor(payment);
                paymentDTOS.add(paymentDTO);
            }
            return new ResponseEntity<>(paymentDTOS, HttpStatus.OK);
        } catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @PutMapping("/add")
    @ApiOperation(value = "Add new payment")
    public ResponseEntity<ResponseMessageDTO> addPayment(@RequestBody PaymentDTO paymentDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try {
            validationPaymentService.validatePaymentDTO(paymentDTO);
        } catch (PaymentValidateException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        try{
            User user = userRepositoryService.getUserFromRequest(request);
            return this.paymentAddXA.addPayment(user, paymentDTO.getProducts_ids(), paymentDTO.getPaymentType());
        } catch (UserNotFoundException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
    }

}
