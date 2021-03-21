package com.blps_lab1.demo.service;

import com.blps_lab1.demo.beans.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class KomusUserDetails implements UserDetails {
    private String email;
    private String password;

    public static KomusUserDetails fromUserToUserDetailsService(User user){
        KomusUserDetails komusDetails = new KomusUserDetails();
        komusDetails.email = user.getEmail();
        komusDetails.password = user.getPassword();
        return komusDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
