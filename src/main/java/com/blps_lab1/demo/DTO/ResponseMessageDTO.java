package com.blps_lab1.demo.DTO;

import java.io.Serializable;

public class ResponseMessageDTO implements Serializable {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
