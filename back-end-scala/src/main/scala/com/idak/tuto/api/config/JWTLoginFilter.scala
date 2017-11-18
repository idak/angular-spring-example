package com.idak.tuto.api.config

import java.io.IOException
import java.util
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import javax.servlet.{FilterChain, ServletException}

import com.fasterxml.jackson.databind.ObjectMapper
import com.idak.tuto.api.model.User
import org.springframework.security.authentication.{AuthenticationManager, UsernamePasswordAuthenticationToken}
import org.springframework.security.core.{Authentication, AuthenticationException, GrantedAuthority}
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.{AntPathRequestMatcher, RequestMatcher}

class JWTLoginFilter(url: String, authManager: AuthenticationManager, antPathRequestMatcher: RequestMatcher)
  extends AbstractAuthenticationProcessingFilter(antPathRequestMatcher) {

  def this(url: String, authManager: AuthenticationManager) {
    this(url, authManager, new AntPathRequestMatcher(url))
    setAuthenticationManager(authManager)
  }

  @throws[AuthenticationException]
  @throws[IOException]
  @throws[ServletException]
  override def attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication = {
    val creds = new ObjectMapper().readValue(req.getInputStream, classOf[User])
    getAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername, creds.getPassword, new util.ArrayList[GrantedAuthority]()))
  }

  @throws[IOException]
  @throws[ServletException]
  override protected def successfulAuthentication(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain, auth: Authentication): Unit = {
    JwtUtil.addAuthentication(res, auth.getName)
  }
}
