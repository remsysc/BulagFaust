package com.sysc.bulag_faust.tag.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateTagRequest(
    @NotBlank(message = "Tag name is required") @Size(min = 2, max = 30, message = "Tag name must be between {min} and {max} characters") @Pattern(regexp = "^[\\w-]+$", message = "Tag name can only contain letters, numbers, and hyphens") String name) {
}
