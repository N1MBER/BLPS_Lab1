package com.blps_lab1.demo.main_server.beans;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "komus_rtoken")
public class RefreshToken {
    @Id
    private Long user_id;

    @NotNull
    private String refreshToken;

    public RefreshToken() {
    }

    public RefreshToken(Long user_id, String refreshToken) {
        this.user_id = user_id;
        this.refreshToken = refreshToken;
    }

    public Long getUser() {
        return user_id;
    }

    public void setUser(Long user_id) {
        this.user_id = user_id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
