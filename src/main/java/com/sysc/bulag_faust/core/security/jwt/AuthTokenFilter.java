package com.sysc.bulag_faust.core.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sysc.bulag_faust.core.security.user.SecurityUserDetails;
import com.sysc.bulag_faust.core.security.user.SecurityUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthTokenFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final SecurityUserDetailsService securityUserDetails;

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getServletPath();
    // Skip filter for public endpoints
    boolean shouldSkip = path.startsWith("/api/v1/auth");
    log.info("Path: {}, Should skip filter: {}", path, shouldSkip);
    return shouldSkip;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = parseJwt(request);
    if (token == null) {
      log.debug("Missing Authorization Header: {}", request.getRequestURI());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    if (!jwtUtils.validateToken(token)) {
      log.warn("Invalid JWT token for URI: {}", request.getRequestURI());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String username = jwtUtils.getUsernameFromToken(token);
    var userDetails = securityUserDetails.loadUserByUsername(username);
    var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());// null because
                                                                                                        // jwt dont want
                                                                                                        // a password
    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    SecurityContextHolder.getContext().setAuthentication(auth); // tells sprign whos logged in for this req.
    if (userDetails instanceof SecurityUserDetails) {
      request.setAttribute("userId", ((SecurityUserDetails) userDetails).getId());
    }
    filterChain.doFilter(request, response);
  }

  private String parseJwt(HttpServletRequest request) {
    String headerAuth = request.getHeader("Authorization");
    if (headerAuth == null || !headerAuth.startsWith("Bearer "))
      return null;
    return headerAuth.substring(7).trim();
  }
}
