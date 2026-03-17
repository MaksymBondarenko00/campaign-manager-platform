package com.cpm.authservice.user.provider;

import com.cpm.authservice.system.security.jwt.JwtUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserProvider {

    public JwtUser getCurrentUser() {
        return (JwtUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
