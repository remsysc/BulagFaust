package com.sysc.bulag_faust.core.Security.config;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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
import com.sysc.bulag_faust.core.Security.user.SecurityUserDetailsService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${api.prefix}")
  private String apiPrefix;

  @Bean
  public SecurityFilterChain securityFilterChain(
      HttpSecurity http,
      DaoAuthenticationProvider daoAuthenticationProvider,
      JwtAuthEntryPoint authEntryPoint,
      AuthTokenFilter authTokenFilter,
      AccessDeniedHandler accessDeniedHandler) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(new CorsConfig().corsConfigurationSource()))
        .exceptionHandling(
            exception -> exception.authenticationEntryPoint(authEntryPoint).accessDeniedHandler(accessDeniedHandler))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            .anyRequest().permitAll())
        .authenticationProvider(daoAuthenticationProvider)
        .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  // @Bean
  // public RoleHierarchy roleHierarchy() {
  // RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
  // // Example: "ROLE_ADMIN > ROLE_USER"
  // roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
  // return roleHierarchy;
  // }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public DaoAuthenticationProvider daoAuthenticationProvider(SecurityUserDetailsService userDetailsService,
      PasswordEncoder passwordEncoder) {

    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler(ObjectMapper mapper) {
    return (request, response, accessDeniedException) -> {
      response.setStatus(HttpServletResponse.SC_FORBIDDEN);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);

      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", Instant.now());
      body.put("status", HttpServletResponse.SC_FORBIDDEN);
      body.put("error", "Forbidden");
      body.put("message", accessDeniedException.getMessage());
      body.put("path", request.getServletPath());
      mapper.writeValue(response.getOutputStream(), body);
    };
  }

}
