package com.sysc.bulag_faust.auth.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record SignUpResponse(UUID id, String username, LocalDateTime createdAt) {

}
