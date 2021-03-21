package com.blps_lab1.demo.DTO;

import java.io.Serializable;

public class UserDTO implements Serializable {
    private Long Id;
    private String email;
    private String password;
    private String name;
    private String surname;

    public void setDTO(Long id, String email, String name, String surname){
        this.email = email;
        this.Id = id;
        this.surname = surname;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return Id;
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

    public void setId(Long id) {
        Id = id;
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
