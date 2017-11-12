package com.idak.tuto.api.config

import java.util

import com.idak.tuto.api.service.UserService
import org.springframework.security.authentication.{AuthenticationProvider, BadCredentialsException, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.{Authentication, GrantedAuthority}
import org.springframework.stereotype.Component

@Component
class CustomAuthentProvider(userService: UserService) extends AuthenticationProvider {

  @throws[BadCredentialsException]
  override def authenticate(authentication: Authentication) : Authentication = {
    val username = authentication.getName
    val password = authentication.getCredentials.toString
    if (userService.authenticate(username, password).nonEmpty)
      new UsernamePasswordAuthenticationToken(username, password, new util.ArrayList[GrantedAuthority])
    else throw new BadCredentialsException("Invalid Credentials !")
  }

  override def supports(authentication: Class[_]) = authentication.equals(classOf[UsernamePasswordAuthenticationToken])
}
