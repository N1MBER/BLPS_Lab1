package com.blps_lab1.demo.main_server.controllers;

import com.blps_lab1.demo.main_server.DTO.OrderDTO;
import com.blps_lab1.demo.main_server.DTO.ProductDTO;
import com.blps_lab1.demo.main_server.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.beans.User;
import com.blps_lab1.demo.main_server.exceptions.ProductValidationException;
import com.blps_lab1.demo.main_server.exceptions.UserNotFoundException;
import com.blps_lab1.demo.main_server.service.OrderRepositoryService;
import com.blps_lab1.demo.main_server.service.UserRepositoryService;
import com.blps_lab1.demo.main_server.validation.ValidationProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/order")
@Api(value = "Order api")
public class OrderController {
    @Autowired
    private OrderRepositoryService orderRepositoryService;

    @Autowired
    private UserRepositoryService userRepositoryService;

    private ResponseMessageDTO message;

    @Autowired
    private Product product;

    @Autowired
    private ValidationProductService validationProductService;

    @PutMapping("/add_order")
    @ApiOperation(value = "Add new order")
    public ResponseEntity<ResponseMessageDTO> addOrder(@RequestBody OrderDTO orderDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            for(ProductDTO productDTO:orderDTO.getProducts()){
                validationProductService.validateProductDTO(productDTO);
            }
        }catch (ProductValidationException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return this.orderRepositoryService.addOrder(user, orderDTO.getProducts());
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @PatchMapping("/order_status")
    @ApiOperation(value = "Change status")
    public ResponseEntity<ResponseMessageDTO> changeOrderStatus(@RequestBody OrderDTO orderDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            return this.orderRepositoryService.updateStatus(orderDTO.getId(), orderDTO.getStatus());
        }catch (DataIntegrityViolationException e){
            this.message.setMessage("Can't find order");
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/order")
    @ApiOperation(value = "Get orders for user")
    public ResponseEntity getOrder(HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return this.orderRepositoryService.getAllOrderForUser(user.getID());
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }
}
