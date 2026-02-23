package com.sysc.bulag_faust.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(

    @NotBlank(message = "Username cant not be blank or null") @Size(min = 3, message = "Minimum of 3 characters") String username,
    @Email @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "Email must have a valid domain like example@domain.com") @NotBlank(message = "Email must not be blank or null") String email,
    @Size(min = 6, message = "Minimum of 6  characters") @NotBlank(message = "Password cant not be blank or null") String password) {

}
