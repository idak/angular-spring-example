package com.idak.tuto.api.config

import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.config.annotation.{CorsRegistry, WebMvcConfigurer, WebMvcConfigurerAdapter}

@Configuration
class ApiSecurityConfig(authentProvider: CustomAuthentProvider) extends WebSecurityConfigurerAdapter{

  @Bean def corsConfigurer: WebMvcConfigurer = new WebMvcConfigurerAdapter() {
    override def addCorsMappings(registry: CorsRegistry): Unit = {
      registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("OPTIONS","GET","POST","DELETE")
        .exposedHeaders(JwtUtil.HEADER_STRING)
    }
  }
  override def configure(http: HttpSecurity): Unit = {
    http.cors()
      .and()
        .csrf().disable()
      .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/auth").permitAll()
        .anyRequest().authenticated()
      // We filter the api/login requests
      .and()
        .addFilterBefore(new JWTLoginFilter("/auth", authenticationManager()), classOf[UsernamePasswordAuthenticationFilter])
        // And filter other requests to check the presence of JWT in header
        .addFilterBefore(new JWTAuthentFilter, classOf[UsernamePasswordAuthenticationFilter])
  }

  override def configure(auth: AuthenticationManagerBuilder): Unit = auth.authenticationProvider(authentProvider)

}
