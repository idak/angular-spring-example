package com.idak.tuto.api

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@SpringBootApplication
class ApiApplication {
  @Bean def passwordEncoder = new BCryptPasswordEncoder
}

object ApiRunner extends App {
  SpringApplication.run(classOf[ApiApplication], args: _*)
}
