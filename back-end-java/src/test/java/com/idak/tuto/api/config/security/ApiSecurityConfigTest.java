package com.idak.tuto.api.config.security;

import com.idak.tuto.api.model.User;
import com.idak.tuto.api.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@Import(value = {ApiSecurityConfig.class, CustomAuthentProvider.class})
@ActiveProfiles("test")
public class ApiSecurityConfigTest {

    @Resource
    MockMvc mvc;

    @MockBean
    UserService userService;

    @Test
    public void loginSuccess() throws Exception{
        User user = User.builder().id(2).username("user").email("uEmail@email.fr").build();
        given(userService.authenticate(anyString(),anyString())).willReturn(Optional.of(user));

        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"username\":\"admin\",\"password\":\"admin\"}"))
                .andExpect(status().isOk());

    }

    @Test
    public void loginFailure() throws Exception{
        given(userService.authenticate(anyString(),anyString())).willReturn(Optional.empty());

        mvc.perform(post("/auth")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content("{\"username\":\"admin\",\"password\":\"admin\"}"))
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void checkEndpointWithoutTokenInHeader() throws Exception{
        List<User> users = Arrays.asList(
                User.builder().id(1).username("admin").email("aEmail@email.fr").build(),
                User.builder().id(2).username("user").email("uEmail@email.fr").build()
        );

        given(userService.get()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isForbidden());

    }

    @Test
    public void checkEndpointWithTokenInHeader() throws Exception{
        List<User> users = Arrays.asList(
                User.builder().id(1).username("admin").email("aEmail@email.fr").build(),
                User.builder().id(2).username("user").email("uEmail@email.fr").build()
        );

        given(userService.get()).willReturn(users);

        mvc.perform(get("/users")
        .header(JwtUtil.HEADER_STRING, JwtUtil.generateToken("admin")))
                .andExpect(status().isOk());

    }
}