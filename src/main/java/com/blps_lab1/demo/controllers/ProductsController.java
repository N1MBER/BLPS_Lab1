package com.blps_lab1.demo.controllers;


import com.blps_lab1.demo.DTO.NotificationDTO;
import com.blps_lab1.demo.DTO.ProductDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.DTO.UserDTO;
import com.blps_lab1.demo.beans.Product;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.exceptions.ProductValidationException;
import com.blps_lab1.demo.exceptions.UserNotFoundException;
import com.blps_lab1.demo.service.CartRepositoryService;
import com.blps_lab1.demo.service.NotificationRepositoryService;
import com.blps_lab1.demo.service.ProductRepositoryService;
import com.blps_lab1.demo.service.UserRepositoryService;
import com.blps_lab1.demo.validation.ValidationProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/lab")
@Api(value = "Petition api")

public class ProductsController {


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
    @ApiOperation(value = "add petition")
    public ResponseEntity<ResponseMessageDTO> addPetition(@RequestBody ProductDTO productDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            validationProductService.validateProductDTO(productDTO);
        }catch (ProductValidationException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        return this.productRepositoryService.saveFromDTO(productDTO, request);
    }

    @GetMapping("/products")
    public ArrayList<ProductDTO> getAllProducts(){
        return this.productRepositoryService.getAllProducts();
    }

    @GetMapping("/users")
    public ArrayList<UserDTO> getAllUsers() {
        return this.userRepositoryService.getAllUsers();
    }

    @GetMapping("/favorites")
    public ResponseEntity getAllFavorites(@RequestBody UserDTO userDTO, HttpServletRequest request){
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
    public ResponseEntity getCart(@RequestBody UserDTO userDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            return this.cartRepositoryService.getAllProductForUser(user.getID());
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }


    @PutMapping("/favorite/{id}")
    public ResponseEntity favorite(@RequestBody UserDTO userDTO, @PathVariable("id") Long id, HttpServletRequest request){
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
    public ResponseEntity addToCart(@RequestBody UserDTO userDTO, @PathVariable("id") Long id, HttpServletRequest request){
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
    public ResponseEntity deleteItem(@RequestBody UserDTO userDTO, @PathVariable("id") Long id, HttpServletRequest request){
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
    public ResponseEntity unfavorite(@RequestBody UserDTO userDTO, @PathVariable("id") Long id, HttpServletRequest request){
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
    public ResponseEntity clearCart(@RequestBody UserDTO userDTO, HttpServletRequest request){
        message = new ResponseMessageDTO();
        try{
            User user = this.userRepositoryService.getUserFromRequest(request);
            boolean flag = this.cartRepositoryService.deleteAllByUserID(user.getID());
            if (flag)
                return new ResponseEntity<>(message, HttpStatus.OK);
            else
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }catch (UserNotFoundException e){
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

}