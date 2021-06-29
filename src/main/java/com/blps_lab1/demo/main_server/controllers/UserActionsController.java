package com.blps_lab1.demo.main_server.controllers;

import com.blps_lab1.demo.main_server.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.main_server.DTO.UserDTO;
import com.blps_lab1.demo.main_server.beans.Product;
import com.blps_lab1.demo.main_server.beans.User;
import com.blps_lab1.demo.main_server.exceptions.ProductNotFoundException;
import com.blps_lab1.demo.main_server.exceptions.UserNotFoundException;
import com.blps_lab1.demo.main_server.service.CartRepositoryService;
import com.blps_lab1.demo.main_server.service.NotificationRepositoryService;
import com.blps_lab1.demo.main_server.service.ProductRepositoryService;
import com.blps_lab1.demo.main_server.service.UserRepositoryService;
import com.blps_lab1.demo.main_server.validation.ValidationProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/user")
@Api(value = "User actions api")
@Profile("dev")
public class UserActionsController {
    @Autowired
    private NotificationRepositoryService notificationRepositoryService;

    @Autowired
    private ProductRepositoryService productRepositoryService;

    @Autowired
    private CartRepositoryService cartRepositoryService;

    @Autowired
    private UserRepositoryService userRepositoryService;

    private ResponseMessageDTO message;

    @Autowired
    private Product product;

    @Autowired
    private ValidationProductService validationProductService;


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
