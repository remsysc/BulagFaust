package com.sysc.bulag_faust.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

    @NotNull @Size(min = 3, message = "Minimum of 3 characters") String username,
    @Email @NotBlank String email,
    @Size(min = 6, message = "Minimum of 6  characters") String password) {

}
