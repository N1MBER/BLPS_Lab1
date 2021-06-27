package com.blps_lab1.demo.validation;

import com.blps_lab1.demo.DTO.UserDTO;
import com.blps_lab1.demo.exceptions.UserValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ValidationUserService {

    public void validateUserDTO(UserDTO userDTO) throws UserValidationException {
        if(!validatePasswordUserDTO(userDTO.getPassword())){
            throw new UserValidationException("Invalid password. Please, try again", HttpStatus.UNAUTHORIZED);
        }else if(!validateEmailUserDTO(userDTO.getEmail())){
            throw new UserValidationException("Invalid email. Please, try again", HttpStatus.BAD_REQUEST);
        }else if(!validateSurnameUserDTO(userDTO.getSurname())){
            throw new UserValidationException("Invalid surname. Please, try again", HttpStatus.BAD_REQUEST);
        }else if(!validateNameUserDTO(userDTO.getName())){
            throw new UserValidationException("Invalid name. Please, try again", HttpStatus.BAD_REQUEST);
        }
    }

    public void validateUserDTO_FOR_AUTH(UserDTO userDTO) throws UserValidationException {
        if(!validatePasswordUserDTO(userDTO.getPassword())){
            throw new UserValidationException("Invalid password. Please, try again", HttpStatus.UNAUTHORIZED);
        }else if(!validateEmailUserDTO(userDTO.getEmail())){
            throw new UserValidationException("Invalid email. Please, try again", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean validatePasswordUserDTO(String password){
        int password_length = password.length();
        return password_length >= 6 && password_length <= 26;
    }

    private boolean validateEmailUserDTO(String email){
        return email.contains("@") && email.length() >= 6;
    }

    private boolean validateNameUserDTO(String name){
        return name.length() > 2 && name.length() < 20;
    }

    private boolean validateSurnameUserDTO(String surname){
        return surname.length() > 2 && surname.length() < 20;
    }

    private static boolean checkIntString(String sequence){
        char[] chars = sequence.toCharArray();
        for (char ch : chars) {
            if (Character.isDigit(ch)) {
                return true;
            }
        }
        return false;
    }
}
