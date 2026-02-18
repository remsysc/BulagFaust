package com.sysc.bulag_faust.auth.dto;

import java.util.UUID;

public record AuthResponse(
    UUID id,
    String tokenType,
    String token,
    long expiresIn) {
}
