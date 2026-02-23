package com.sysc.bulag_faust.core.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
    @Email @NotBlank(message = "Email cannot be blank or null") String email,
    @Size(min = 6, message = "Password must be at least 6 charachers long") @NotBlank(message = "Password cannot be blank or null") String password) {

}
