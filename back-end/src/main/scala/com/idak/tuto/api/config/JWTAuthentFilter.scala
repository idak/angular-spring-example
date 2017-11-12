package com.idak.tuto.api.config

import java.io.IOException
import javax.servlet.http.HttpServletRequest
import javax.servlet.{FilterChain, ServletException, ServletRequest, ServletResponse}

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean

class JWTAuthentFilter extends GenericFilterBean{

  @throws[IOException]
  @throws[ServletException]
  override def doFilter(request: ServletRequest, response: ServletResponse, filterChain: FilterChain): Unit = {
    val authentication = JwtUtil.getAuthentication(request.asInstanceOf[HttpServletRequest])
    SecurityContextHolder.getContext.setAuthentication(authentication)
    filterChain.doFilter(request, response)
  }
}
