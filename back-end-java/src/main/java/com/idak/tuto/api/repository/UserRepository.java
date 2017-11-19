package com.idak.tuto.api.repository;

import com.idak.tuto.api.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
