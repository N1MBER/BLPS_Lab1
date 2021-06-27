package com.blps_lab1.demo.main_server.validation;

import com.blps_lab1.demo.main_server.DTO.PaymentDTO;
import com.blps_lab1.demo.main_server.exceptions.PaymentValidateException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ValidationPaymentService {
    public void validatePaymentDTO(PaymentDTO paymentDTO) throws PaymentValidateException {
        if (!validateDate(paymentDTO.getDate())){
            throw new PaymentValidateException("Invalid date", HttpStatus.BAD_REQUEST);
        } else if (!validateProducts(paymentDTO.getProducts_ids())){
            throw new PaymentValidateException("Payment can't be empty", HttpStatus.BAD_REQUEST);
        }
    }

    public boolean validateProducts(List<Long> productDTOS) {return productDTOS.size() > 0;}
    public boolean validateDate(Date date) {
        Date now_date = new Date();
        return now_date.after(date);
    }
}
