package com.sysc.bulag_faust.core.security.user;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sysc.bulag_faust.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SecurityUserDetails implements UserDetails {

  private UUID id;
  private String email;
  private String password;
  private Collection<GrantedAuthority> authorities;

  public static SecurityUserDetails buildUserDetails(User user) {
    List<GrantedAuthority> authorities = user.getRoles()
        .stream().map(role -> new SimpleGrantedAuthority(role.getName()))
        .collect(Collectors.toList());

    return new SecurityUserDetails(user.getId(), user.getEmail(), user.getPassword(),
        Collections.unmodifiableList(authorities));
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {

    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public UUID getId() {
    return id;
  }
}
