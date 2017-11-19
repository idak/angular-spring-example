package com.idak.tuto.api.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Objects;

import static java.util.Collections.emptyList;

@Slf4j
public class JwtUtil {
    private static final String SECRET = "Your&secret!";
    public static final String HEADER_STRING = "Authorization";

    private JwtUtil(){ }
    /**
     * extract username from token
     * @param token
     */
    public static String getUsernameFromToken(String token){
        String username = null;
        try {
            username = getClaimsFromToken(token).getSubject();
        } catch (Exception e) {
            log.error("{}", e);
        }

        return username;
    }

    /**
     * Generate JWT Token from user properties
     * @param username
     */
    public static String generateToken(String username) {
        Instant instant = LocalDateTime.now().plusMonths(2).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(Date.from(instant))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     *  Check Token validity
     * @param token
     */
    public static Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }


    /**
     * Add token to http Header
     * @param res  HttpServletResponse
     * @param username String
     */
    public static void addAuthentication(HttpServletResponse res, String username) {
        String token = generateToken(username);
        res.addHeader(HEADER_STRING, token);
    }

    /**
     * Create Spring Security authentication if token is valid
     * @param request HttpServletRequest
     * @return Authentication
     */
    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (Objects.nonNull(token)) {
            String user = getClaimsFromToken(token).getSubject();
            return Objects.nonNull( user ) ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }
}
