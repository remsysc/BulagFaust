package com.sysc.bulag_faust.core.security;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysc.bulag_faust.core.security.jwt.AuthTokenFilter;
import com.sysc.bulag_faust.core.security.jwt.JwtAuthEntryPoint;
import com.sysc.bulag_faust.core.security.user.SecurityUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, AuthTokenFilter authTokenFilter,
      DaoAuthenticationProvider daoAuthenticationProvider, JwtAuthEntryPoint jwtAuthEntryPoint,
      AccessDeniedHandler accessDeniedHandler) throws Exception {
    http
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .exceptionHandling(
            exception -> exception.authenticationEntryPoint(jwtAuthEntryPoint).accessDeniedHandler(accessDeniedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/api/v1/auth/**").permitAll() // ← /api/v1/auth/login
            .requestMatchers("/api/v1/categories/**").authenticated() // ← /api/v1/category
            .requestMatchers("/api/v1/tags/**").authenticated()
            .anyRequest().denyAll())
        .authenticationProvider(daoAuthenticationProvider)
        .addFilterBefore(authTokenFilter,
            UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  // compare the password from the database
  // has the input password, then compare
  // creates a security context
  // every future request uses this context to verify access
  @Bean
  public PasswordEncoder passWordEnconder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
    return config.getAuthenticationManager();
  }

  @Bean
  DaoAuthenticationProvider authenticationProvider(SecurityUserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setHideUserNotFoundExceptions(false);// logs invalid user
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler(ObjectMapper mapper) {
    return (request, response, ex) -> {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", Instant.now());
      body.put("status", HttpServletResponse.SC_FORBIDDEN);
      body.put("error", "Forbidden");
      body.put("message", ex.getMessage());
      body.put("path", request.getServletPath());
      mapper.writeValue(response.getOutputStream(), body);
    };
  }

}
