package com.sysc.bulag_faust.core.response;

import java.util.UUID;

public record AuthResponse(
    UUID id,
    String tokenType,
    String token,
    long expiresIn) {
}
