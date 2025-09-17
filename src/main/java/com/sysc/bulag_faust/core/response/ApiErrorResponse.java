package com.sysc.bulag_faust.core.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class ApiErrorResponse {
    private  String code;
    private  String message;
    private LocalDateTime timestamp;
}
