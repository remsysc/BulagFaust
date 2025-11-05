package com.sysc.bulag_faust.core.Security.jwt;

import com.sysc.bulag_faust.core.Security.user.SecurityUserDetails;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//Token Generation & Validation
/*
The “token factory” and “token inspector.”
It doesn’t care how you authenticated —
it takes an Authentication object
(already containing your SecurityUserDetails)
and produces a signed JWT for stateless sessions.
It also verifies and decodes incoming JWTs.

*/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {


    @Value("${auth.token.jwtSecret}")
    private String jwtSecret;

    @Value("${auth.token.expirationInMils}")
    private long expirationTime;

    private SecretKey key;
    private JwtParser jwtParser;

    @PostConstruct
    void init() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(jwtSecret.trim());
            this.key = Keys.hmacShaKeyFor(keyBytes);

            this.jwtParser = Jwts.parser()
                    .verifyWith(key)
                    .clockSkewSeconds(30)
                    .build();
            log.info("JWT utilities initialized successfully");
        } catch (Exception e) {
            log.error("Failed to initialize JWT utilities");
            throw new IllegalArgumentException("JWT configuration error", e);
        }

    }


    //Extracts the authenticated SecurityUserDetails.
    //
    //Embeds the user’s email as subject, plus id and roles as custom claims.
    //
    //Sets issue and expiration timestamps.
    //
    //Signs the token with an HS256 key derived from your jwtSecret.

    /**
     * Generate a signed JWT for the authenticated user
     */

    public String generateTokenForUser(Authentication authentication) {

        try {
            SecurityUserDetails userDetails = (SecurityUserDetails) authentication.getPrincipal();
            String username = authentication.getName();

            List<String> roles = authentication
                    .getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();
            Date now = new Date();
            Date expiry = new Date(System.currentTimeMillis() + expirationTime);

            return Jwts.builder()
                    .subject(username)
                    .claim("userId", userDetails.getId())
                    .claim("email", userDetails.getUsername())
                    .claim("roles", roles)
                    .issuedAt(now)
                    .expiration(expiry)
                    .signWith(key, Jwts.SIG.HS256)
                    .compact();
        } catch (Exception e) {
            log.error("Error generating JWT token for user: {}", authentication.getName());
            throw new RuntimeException("Could not generate JWT token", e);
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return jwtParser
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException e) {
            log.debug("Cannot get username from token: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public boolean validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid JWT token: {}", e.getMessage());
            return false;

        }

    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        try {
            return jwtParser.parseSignedClaims(token)
                    .getPayload()
                    .get("roles", List.class);

        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Cannot get roles from token: {}", e.getMessage());
            return Collections.emptyList();

        }


    }
   public boolean isTokenExpired(String token){
        try{
            Date expiration = jwtParser.parseSignedClaims(token)
                    .getPayload()
                    .getExpiration();
            return  expiration.before(new Date());
        } catch (JwtException  | IllegalArgumentException e) {
            return  true;
        }
   }

}
