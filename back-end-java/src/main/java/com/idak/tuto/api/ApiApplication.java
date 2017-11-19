package com.idak.tuto.api;

import com.idak.tuto.api.config.security.LoadDataCondition;
import com.idak.tuto.api.model.User;
import com.idak.tuto.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;

@Slf4j
@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
    @Conditional(LoadDataCondition.class)
	public CommandLineRunner loadData(UserRepository repository, PasswordEncoder encoder){
		return args -> {
			log.info("----------------- Save users data---------------------");
			LinkedList users = new LinkedList();
			users.add(User.builder()
					.username("admin")
					.password(encoder.encode("admin"))
					.email("admin@gmail.com")
					.build());

			users.add(User.builder()
					.username("user")
					.password(encoder.encode("user"))
					.email("user@gmail.com")
					.build());
			repository.save(users);
			log.info("----------------- All users data saved ---------------");
		};
	}

}
