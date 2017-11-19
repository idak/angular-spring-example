package com.idak.tuto.api.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idak.tuto.api.model.User;
import com.idak.tuto.api.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserResource.class)
@ActiveProfiles("test")
public class UserResourceTest {

    @Resource
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void getAllUsers() throws Exception {
        List<User> users = Arrays.asList(
                User.builder().id(1).username("admin").email("aEmail@email.fr").build(),
                User.builder().id(2).username("user").email("uEmail@email.fr").build()
        );

        given(userService.get()).willReturn(users);

        mvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].username").value("admin"))
                .andExpect(jsonPath("$.[0].email").value("aEmail@email.fr"))
                .andExpect(jsonPath("$.[0].password").isEmpty())
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].username").value("user"))
                .andExpect(jsonPath("$.[1].email").value("uEmail@email.fr"))
                .andExpect(jsonPath("$.[1].password").isEmpty());
    }

    @Test
    public void getOne() throws Exception {
        User user = User.builder().id(1).username("admin").email("aEmail@email.fr").build();

        given(userService.get(1)).willReturn(user);

        mvc.perform(get("/users/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("aEmail@email.fr"))
                .andExpect(jsonPath("$.password").isEmpty());
    }

    @Test
    public void save() throws Exception {
        User user = User.builder().id(1).username("admin").email("aEmail@email.fr").build();
        given(userService.create(user)).willReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("aEmail@email.fr"))
                .andExpect(jsonPath("$.password").isEmpty());
    }

    @Test
    public void deleteOne() throws Exception {
        doNothing().when(userService).delete(1);
        mvc.perform(delete("/users/1")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void updateOne() throws Exception {
        User user = User.builder().id(1).username("admin").email("aEmail@email.fr").build();
        given(userService.update(user)).willReturn(user);

        mvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("admin"))
                .andExpect(jsonPath("$.email").value("aEmail@email.fr"))
                .andExpect(jsonPath("$.password").isEmpty());
    }

}