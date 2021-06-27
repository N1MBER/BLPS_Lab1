package com.blps_lab1.demo.exceptions;

import org.springframework.http.HttpStatus;

public class UserValidationException extends Throwable {
    private String errMessage;
    private HttpStatus errStatus;

    public UserValidationException(String errMessage, HttpStatus errStatus){
        super();
        this.errMessage = errMessage;
        this.errStatus = errStatus;
    }

    public HttpStatus getErrStatus() {
        return errStatus;
    }

    public void setErrStatus(HttpStatus errStatus) {
        this.errStatus = errStatus;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }
}
