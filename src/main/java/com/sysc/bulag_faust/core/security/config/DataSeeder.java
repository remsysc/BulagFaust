package com.sysc.bulag_faust.core.security.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataSeeder {

  private final DataSeederService seedService;

  @Bean
  CommandLineRunner seedRolesAndAdmin() {
    return args -> seedService.seed(); // delegate to a @Transactional service
  }
}
