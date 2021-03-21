package com.blps_lab1.demo.service;

import com.blps_lab1.demo.beans.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class KomusUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepositoryService userRepositoryService;


    @Override
    public KomusUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepositoryService.getUserRepository().findByEmail(email);
        return KomusUserDetails.fromUserToUserDetailsService(user);
    }
}
