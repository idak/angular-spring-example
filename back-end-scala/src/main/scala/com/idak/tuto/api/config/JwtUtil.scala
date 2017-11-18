package com.idak.tuto.api.config

import java.time.{LocalDateTime, ZoneId}
import java.util
import java.util.Date
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import io.jsonwebtoken.{Claims, Jwts, SignatureAlgorithm}
import org.slf4j.{Logger, LoggerFactory}
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.{Authentication, GrantedAuthority}

object JwtUtil {
  private val log: Logger = LoggerFactory.getLogger(getClass)
  private val SECRET = "Your&secret!"
  val HEADER_STRING = "Authorization"


  /**
    * Generate JWT Token from user properties
    * @param username
    */
  def generateToken(username: String): String = {
    val instant = LocalDateTime.now.plusMonths(2).atZone(ZoneId.systemDefault).toInstant
    Jwts.builder
      .setSubject(username)
      .setExpiration(Date.from(instant))
      .signWith(SignatureAlgorithm.HS512, SECRET)
      .compact
  }

  /**
    * Check Token validity
    * @param token
    */
  def getClaimsFromToken(token: String): Claims = Jwts.parser.setSigningKey(SECRET).parseClaimsJws(token).getBody

  /**
    * Add token to http Header
    * @param res      HttpServletResponse
    * @param username String
    */
  def addAuthentication(res: HttpServletResponse, username: String): Unit = res.addHeader(HEADER_STRING, generateToken(username))

  /**
    * Create Spring Security authentication if token is valid
    * @param request HttpServletRequest
    * @return Authentication
    */
  def getAuthentication(request: HttpServletRequest): Authentication = {
    val token = request.getHeader(HEADER_STRING)
    if (token != null && getClaimsFromToken(token).getSubject  != null)
      createAuthent( getClaimsFromToken(token).getSubject) else null
  }

  private def createAuthent(username: String) : Authentication = {
    new UsernamePasswordAuthenticationToken(username, null, new util.ArrayList[GrantedAuthority]())
  }

}
