package com.blps_lab1.demo.controllers;

import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.DTO.UserDTO;
import com.blps_lab1.demo.exceptions.UserValidationException;
import com.blps_lab1.demo.service.UserRepositoryService;
import com.blps_lab1.demo.validation.ValidationUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class AuthorizationController {

    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private ValidationUserService validationUserService;

    private ResponseMessageDTO message;


    @PostMapping("/register")
    @ApiOperation(value = "Register new user")
    public ResponseEntity<ResponseMessageDTO> register(@RequestBody UserDTO userDTO) {
        this.message = new ResponseMessageDTO();
        try{
            validationUserService.validateUserDTO(userDTO);
        }catch (UserValidationException e) {
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        return userRepositoryService.registerUserDTO(userDTO);
    }

    @PostMapping("/auth")
    @ApiOperation(value = "Authorization")
    public ResponseEntity auth(@RequestBody UserDTO userDTO){
        this.message = new ResponseMessageDTO();
        try{
            validationUserService.validateUserDTO_FOR_AUTH(userDTO);
        }catch (UserValidationException e) {
            this.message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(this.message, e.getErrStatus());
        }
        return userRepositoryService.authUserDTO(userDTO);
    }
}