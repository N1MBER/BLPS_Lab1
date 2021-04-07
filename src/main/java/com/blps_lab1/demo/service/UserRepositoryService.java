package com.blps_lab1.demo.service;

import com.blps_lab1.demo.DTO.ResponseMessageDTO;
import com.blps_lab1.demo.DTO.TokenObject;
import com.blps_lab1.demo.DTO.UserDTO;
import com.blps_lab1.demo.beans.User;
import com.blps_lab1.demo.exceptions.UserNotFoundException;
import com.blps_lab1.demo.repository.UserRepository;
import com.blps_lab1.demo.utils.JWTUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class UserRepositoryService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private User user;
    @Autowired
    private DTOConverter dtoConverter;

    Logger logger = LogManager.getLogger(UserRepositoryService.class);

    public UserRepository getUserRepository(){
        return userRepository;
    }
    public User getUserFromRequest(HttpServletRequest request) throws UserNotFoundException{
        String token = jwtUtils.getTokenFromRequest(request);
        String email = jwtUtils.getEmailFromToken(token);
        try {
            User user = this.findByEmail(email);
            logger.log(Level.INFO, "getting user from request " + user.getEmail());
            return user;
        }catch (UserNotFoundException e){
            throw e;
        }
    }

    public ResponseEntity<ResponseMessageDTO> registerUserDTO(UserDTO userDTO){
        ResponseMessageDTO message = new ResponseMessageDTO();
        user = dtoConverter.userFromDTOConvertor(userDTO);
        String answerText = "";
        try{
            this.save(user);
            message.setMessage("Вы были успешно зарегестрированы");
        }catch (DataIntegrityViolationException e){
            if(e.getCause().getClass() == ConstraintViolationException.class){
                answerText = "Пользователь с таким email уже существует";
            }else{
                answerText = "УПС! Произошла ошибка, пожалуйста, попробуйте позднее";
            }
            message.setMessage(answerText);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    public ResponseEntity<ResponseMessageDTO> authUserDTO(UserDTO userDTO){
        ResponseMessageDTO message = new ResponseMessageDTO();
        try {
            user = this.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
            TokenObject token = new TokenObject(jwtUtils.generateToken(user.getEmail()));
            message.setMessage(token.getToken());
            return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
        }catch (UserNotFoundException e){
            message.setMessage(e.getErrMessage());
            return new ResponseEntity<>(message, e.getErrStatus());
        }
    }

    public void save(User user)  {
        this.userRepository.save(user);
    }

    public User findById(Long id) throws UserNotFoundException{
        User user = this.userRepository.findByID(id);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким email не найден", HttpStatus.BAD_REQUEST);
        }
        return user;
    }


    public User findByEmailAndPassword(String email, String password) throws UserNotFoundException{
        User user  = this.userRepository.findByEmailAndPassword(email, password);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким email не найден", HttpStatus.BAD_REQUEST);
        }
        return user;
    }

    public User findByEmail(String email) throws UserNotFoundException{
        User user = this.userRepository.findByEmail(email);
        if(user == null){
            throw new UserNotFoundException("Пользователь с таким email не найден. Ошибка авторизации", HttpStatus.UNAUTHORIZED);
        }
        return user;
    }

    public ArrayList<UserDTO> getAllUsers(){
        ArrayList<User> users = this.userRepository.findAll();
        ArrayList<UserDTO> userDTOS = new ArrayList<>();
        for (User user: users){
            userDTOS.add(dtoConverter.userDTOConvertor(user));
        }
        return userDTOS;
    }
}
