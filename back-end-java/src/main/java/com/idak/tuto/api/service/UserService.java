package com.idak.tuto.api.service;

import com.idak.tuto.api.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * User Service implementation
 */
@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    private PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        User admin = User.builder()
                .id(1)
                .username("admin")
                .password("admin")
                .email("admin@gmail.com")
                .build();
        this.create(admin);
    }

    /**
     * Create new User
     * @param user
     * @return
     */
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        users.add(user);
        return user;
    }

    /**
     * Delete User
     * @param user
     */
    public void delete(User user) {
        users.remove(user);
    }

    /**
     * Delete User by id
     * @param id
     */
    public void delete(int id) {
        Optional<User> user = users.stream().filter(u -> u.getId() == id)
                .findFirst();
        if (user.isPresent()) users.remove(user.get());
    }

    public List<User> get() {
        return users;
    }

    /**
     * Find User by id
     * @param id
     * @return User
     */
    public User get(int id) {
        Optional<User> user = users.stream().filter(u -> u.getId() == id)
                .findFirst();
        if (user.isPresent()) return user.get();
        return null;
    }

    /**
     * Update user
     * @param user
     */
    public void update(User user) {
        users.stream()
                .filter(u -> u.getId() == user.getId())
                .findFirst()
                .ifPresent( u -> {
                    users.remove(u);
                    users.add(user);
                });
    }

    /**
     *  Check username and password
     * @param username
     * @param password
     * @return Optional
     */
    public Optional<User> authenticate(String username, String password){
        if(Objects.isNull(username) || Objects.isNull(password)) {
            return Optional.empty();
        }

        return this.users.stream()
                .filter( u -> username.equals(u.getUsername()) && passwordEncoder.matches(password, u.getPassword()))
                .findFirst();
    }
}
