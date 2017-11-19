package com.idak.tuto.api.service;

import com.idak.tuto.api.repository.UserRepository;
import com.idak.tuto.api.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * User Service implementation
 */
@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Create new User
     * @param user : user to create
     * @return User : saved user
     */
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Delete User
     * @param user
     */
    public void delete(User user) {
        userRepository.delete(user);
    }

    /**
     * Delete User by id
     * @param id
     */
    public void delete(int id) {
        userRepository.delete(id);
    }

    public List<User> get() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Find User by id
     * @param id
     * @return User
     */
    public User get(int id) {
        return userRepository.findOne(id);
    }

    /**
     * Update user
     * @param user
     * @return User
     */
    public User update(User user) {
        return userRepository.save(user);
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

        Optional<User> authenticatedUse = userRepository.findByUsername(username);
        if(authenticatedUse.isPresent()
                && passwordEncoder.matches(password, authenticatedUse.get().getPassword())){
            return authenticatedUse;
        }

        return Optional.empty();

    }
}
