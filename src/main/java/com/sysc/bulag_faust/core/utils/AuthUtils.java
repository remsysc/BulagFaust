package com.sysc.bulag_faust.core.utils;

import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.sysc.bulag_faust.core.security.user.SecurityUserDetails;

@Component
public class AuthUtils {

  public UUID getAuthenticateUserID() {
    SecurityUserDetails userDetails = (SecurityUserDetails) SecurityContextHolder
        .getContext().getAuthentication().getPrincipal();

    // ADD THIS
    System.out.println("User ID: " + userDetails.getId());
    System.out.println("User email: " + userDetails.getUsername());

    return userDetails.getId();
  }
}
