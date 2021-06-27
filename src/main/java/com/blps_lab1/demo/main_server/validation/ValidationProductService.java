package com.blps_lab1.demo.main_server.validation;

import com.blps_lab1.demo.main_server.DTO.ProductDTO;
import com.blps_lab1.demo.main_server.exceptions.ProductValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ValidationProductService {
    public void validateProductDTO(ProductDTO productDTO) throws ProductValidationException {
        if(!validateNameProductDTO(productDTO.getName())){
            throw new ProductValidationException("Invalid name", HttpStatus.BAD_REQUEST);
        }else if(!validatePriceProductDTO(productDTO.getPrice())){
            throw new ProductValidationException("Price must be a positive number", HttpStatus.BAD_REQUEST);
        }

    }

    public boolean validateNameProductDTO(String name){
        return name.length() > 5 && name.length() < 50;
    }

    public boolean validatePriceProductDTO(Double price){
        return price > 0;
    }
}
