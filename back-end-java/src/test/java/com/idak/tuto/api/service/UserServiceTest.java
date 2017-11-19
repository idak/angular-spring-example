package com.idak.tuto.api.service;

import com.idak.tuto.api.model.User;
import com.idak.tuto.api.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        passwordEncoder = new BCryptPasswordEncoder();
    }


    @Test
    public void authenticate_success() throws Exception {
        User user = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Optional<User> authenticatedUser = userService.authenticate("admin", "admin");
        assertTrue(authenticatedUser.isPresent());

    }

    @Test
    public void authenticateFailWithEmptyUsername() throws Exception {
        Optional<User> authenticatedUser = userService.authenticate(null, "admin");
        assertFalse(authenticatedUser.isPresent());
    }

    @Test
    public void authenticateFailWithEmptyPassword() throws Exception {
        Optional<User> authenticatedUser = userService.authenticate("admin", null);
        assertFalse(authenticatedUser.isPresent());
    }

    @Test
    public void authenticateFailWithInvalidPassword() throws Exception {
        User user = User.builder()
                .password(passwordEncoder.encode("admin"))
                .build();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        Optional<User> authenticatedUser = userService.authenticate("admin", "adminFail");
        assertFalse(authenticatedUser.isPresent());
    }

    @Test
    public void authenticateFailWithInvalidUsername() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        Optional<User> authenticatedUser = userService.authenticate("adminInvalid", "admin");
        assertFalse(authenticatedUser.isPresent());
    }

    @Test
    public void createUserAndUpdatIt(){
        String password = passwordEncoder.encode("admin");
        User user = User.builder()
                .username("admin")
                .password(password)
                .build();
        when(userRepository.save(user)).thenReturn(user);
        User createdUser = userService.create(user);
        assertThat(createdUser.getUsername(), is("admin"));
        createdUser.setEmail("admin@gmail.com");
        userService.update(createdUser);
        verify(userRepository, times(2)).save(user);
    }

    @Test
    public void getAll(){
        List<User> users = Arrays.asList(
                User.builder().id(1).username("admin").email("email@email.fr").build(),
                User.builder().id(2).username("user").email("email@email.fr").build()
        );

        when(userRepository.findAll()).thenReturn(users);
        List<User> results = userService.get();
        assertThat(results.size(), is(2));
    }

    @Test
    public void getById(){
        User user = User.builder().id(1).username("admin").email("email@email.fr").build();
        when(userRepository.findOne(anyInt())).thenReturn(user);
        User found = userService.get(1);
        assertThat(found.getEmail(), is("email@email.fr"));
    }

    @Test
    public void deleteUser(){
        doNothing().when(userRepository).delete(anyInt());
        userService.delete(User.builder().id(1).build());
    }

    @Test
    public void deleteUserById(){
        doNothing().when(userRepository).delete(anyInt());
        userService.delete(1);
    }

}