package com.sysc.bulag_faust.core.utils;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sysc.bulag_faust.core.security.user.SecurityUserDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthUtils {

  public UUID getAuthenticateUserID() {
    SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();
    log.debug("Authenticated user ID: {}", userDetails.getId());
    return userDetails.getId();
  }
}
