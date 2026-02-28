package com.sysc.bulag_faust;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BulagFaustApplication {

  public static void main(String[] args) {
    SpringApplication.run(BulagFaustApplication.class, args);

    // LEARN: spring security and sql queries
    // TODO: add rate limiting, revocation, lockout mechanism
    // TODO: add testing, JUNIT / mockito
  }
}
