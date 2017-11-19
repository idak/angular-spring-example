package com.idak.tuto.api.repository;

import com.idak.tuto.api.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRepositoryTest {
    @Resource
    private UserRepository userRepository;

    @Test
    public void findByUsername() throws Exception {
        Optional<User> admin = userRepository.findByUsername("admin");
        assertTrue(admin.isPresent());

    }

}