package com.idak.tuto.api.config.security;

import com.idak.tuto.api.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;

@Component
public class CustomAuthentProvider implements AuthenticationProvider {

    @Resource
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(userService.authenticate(username, password).isPresent()){
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        }else{
            throw new BadCredentialsException("Invalid Credentials !");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
