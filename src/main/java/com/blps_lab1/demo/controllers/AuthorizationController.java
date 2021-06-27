package com.blps_lab1.demo.controllers;

import com.blps_lab1.demo.DTO.RefreshDTO;
import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.DTO.UserDTO;
import com.blps_lab1.demo.beans.TokenObject;
import com.blps_lab1.demo.exceptions.UserValidationException;
import com.blps_lab1.demo.service.KomusUserDetailsService;
import com.blps_lab1.demo.service.UserRepositoryService;
import com.blps_lab1.demo.utils.JWTUtils;
import com.blps_lab1.demo.validation.ValidationUserService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class  AuthorizationController {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private KomusUserDetailsService komusUserDetailsService;

    @Autowired
    private ValidationUserService validationUserService;


    @Autowired
    private JWTUtils jwTutils;


    @PostMapping("/register")
    @ApiOperation(value = "Register new user")
    public ResponseEntity<ResponseMessageDTO> register(@RequestBody UserDTO userDTO) {
        ResponseMessageDTO message = new ResponseMessageDTO();
        try{
            validationUserService.validateUserDTO(userDTO);
        }catch (UserValidationException e) {
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
        return komusUserDetailsService.registerUserDTO(userDTO);
    }

    @PostMapping("/")
    @ApiOperation(value = "Authorization")
    public ResponseEntity auth(@RequestBody UserDTO userDTO){
        ResponseMessageDTO message = new ResponseMessageDTO();
        try{
            validationUserService.validateUserDTO_FOR_AUTH(userDTO);
        }catch (UserValidationException e) {
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
        return komusUserDetailsService.authUserDTO(userDTO);
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "Refresh")
    public ResponseEntity<TokenObject> refresh(HttpServletRequest servletRequest, @RequestBody RefreshDTO refreshDTO){
        String token = refreshDTO.getRefresh();
        ResponseMessageDTO message = new ResponseMessageDTO();
        try{
            jwTutils.validateToken(token);
            return jwTutils.refreshToken(token);
        }catch (ExpiredJwtException e) {
            message.setMessage("Refresh token expired time ended, please authorize again");
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }catch (SignatureException e){
            message.setMessage("Refresh token is not valid, please try again");
            return new ResponseEntity(message, HttpStatus.BAD_REQUEST);
        }

    }
}