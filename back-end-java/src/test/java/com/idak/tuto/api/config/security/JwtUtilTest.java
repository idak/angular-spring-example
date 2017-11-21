package com.idak.tuto.api.config.security;

import io.jsonwebtoken.Claims;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class JwtUtilTest {

    private String token;

    @Before
    public void setUp() {
        token = JwtUtil.generateToken("admin");
    }

    @Test
    public void generateToken() {
        assertNotNull(JwtUtil.generateToken("user"));
    }

    @Test
    public void getUsernameFromToken() {
        String username = JwtUtil.getUsernameFromToken(token);
        assertThat(username, is("admin"));
    }

    @Test
    public void getUsernameFromTokenWithNotValidToken () {
        String username = JwtUtil.getUsernameFromToken("notvalid");
        assertNull(username);
    }

    @Test
    public void getClaimsFromTokenValidExpirationDate() {
        Claims claims = JwtUtil.getClaimsFromToken(token);
        assertTrue(claims.getExpiration().after(new Date()));
    }
}