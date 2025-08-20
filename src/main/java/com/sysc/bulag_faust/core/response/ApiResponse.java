package com.sysc.bulag_faust.core.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    String message;
    T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null);
    }
}
