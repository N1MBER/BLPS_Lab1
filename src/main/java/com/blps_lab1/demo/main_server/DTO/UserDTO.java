package com.blps_lab1.demo.main_server.DTO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private String email;
    private String password;
    private String name;
    private String surname;

    public UserDTO(String email, String name, String surname, String password){
        this.email = email;
        this.surname = surname;
        this.name = name;
        this.password = password;
    }

    public UserDTO(){}

    public void setDTO(String email, String name, String surname){
        this.email = email;
        this.surname = surname;
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
