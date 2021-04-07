package com.blps_lab1.demo.controllers;


import com.blps_lab1.demo.DTO.*;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.exceptions.ProductValidationException;
import com.blps_lab1.demo.exceptions.UserNotFoundException;
import com.blps_lab1.demo.service.*;
import com.blps_lab1.demo.validation.ValidationProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/api")
@Api(value = "Product api")

public class ProductsController {

    @Autowired
    private OrderRepositoryService orderRepositoryService;
    @Autowired
    private ProductRepositoryService productRepositoryService;
    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private NotificationRepositoryService notificationRepositoryService;
    @Autowired
    private CartRepositoryService cartRepositoryService;
    private ResponseMessageDTO message;



    @Autowired
    private Product product;

    @Autowired
    private ValidationProductService validationProductService;



    @PutMapping("/add_product")
    @ApiOperation(value = "Add new product")
    public ResponseEntity<ResponseMessageDTO> addProduct(@RequestBody ProductDTO productDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            validationProductService.validateProductDTO(productDTO);
        }catch (ProductValidationException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        return this.productRepositoryService.saveFromDTO(productDTO, request);
    }

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
    @GetMapping("/products")
    @ApiOperation(value = "Get list of products")
    public ArrayList<ProductDTO> getAllProducts(){
        return this.productRepositoryService.getAllProducts();
    }

    @GetMapping("/users")
    @ApiOperation(value = "Get list of users")
    public ArrayList<UserDTO> getAllUsers() {
        return this.userRepositoryService.getAllUsers();
    }

    @GetMapping("/favorites")
    @ApiOperation(value = "Get favorites for user")
    public ResponseEntity getAllFavorites(HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return this.notificationRepositoryService.getAllNotificationForUser(user.getID());
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @GetMapping("/cart")
    @ApiOperation(value = "Get cart for user")
    public ResponseEntity getCart(HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return this.cartRepositoryService.getAllProductForUser(user.getID());
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
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


    @PutMapping("/favorite/{id}")
    @ApiOperation(value = "Add new favorite")
    public ResponseEntity favorite(@PathVariable("id") Long id, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            try{
                product = this.productRepositoryService.findByID(id);
            }catch (ProductNotFoundException e){
                this.message.setMessage(e.getErrMessage());
                return new ResponseEntity<>(message, e.getErrStatus());
            }
            return this.notificationRepositoryService.addToNotification(user, product);
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @PutMapping("/add_to_cart/{id}")
    @ApiOperation(value = "Add new product")
    public ResponseEntity addToCart(@PathVariable("id") Long id, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            try{
                product = this.productRepositoryService.findByID(id);
            }catch (ProductNotFoundException e){
                this.message.setMessage(e.getErrMessage());
                return new ResponseEntity<>(message, e.getErrStatus());
            }
            return this.cartRepositoryService.addToCart(user, product);
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @GetMapping("/product/{id}")
    @ApiOperation(value = "Get product by id")
    public ResponseEntity getProductById(@PathVariable("id") Long id){
        message = new ResponseMessageDTO();
        try {
            ProductDTO productDTO = this.productRepositoryService.findByIDToResponse(id);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }catch (ProductNotFoundException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @DeleteMapping("/cart/{id}")
    @ApiOperation(value = "Delete item by id")
    public ResponseEntity deleteItem(@PathVariable("id") Long id, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            try{
                product = this.productRepositoryService.findByID(id);
            }catch (ProductNotFoundException e){
                this.message.setMessage(e.getErrMessage());
                return new ResponseEntity<>(message, e.getErrStatus());
            }
            return this.cartRepositoryService.deleteFromCart(user, product);
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @DeleteMapping("/unfavorite/{id}")
    @ApiOperation(value = "Delete item from favorite list")
    public ResponseEntity unfavorite(@PathVariable("id") Long id, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            try{
                product = this.productRepositoryService.findByID(id);
            }catch (ProductNotFoundException e){
                this.message.setMessage(e.getErrMessage());
                return new ResponseEntity<>(message, e.getErrStatus());
            }
            return this.notificationRepositoryService.deleteFromNotifications(user, product);
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    @DeleteMapping("/clear_cart")
    @ApiOperation(value = "Clear all items from cart")
    public ResponseEntity clearCart(HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return this.cartRepositoryService.clearCart(user);
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

}