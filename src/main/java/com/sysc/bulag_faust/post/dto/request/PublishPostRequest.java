package com.sysc.bulag_faust.post.dto.request;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PublishPostRequest(
    @NotBlank(message = "Title is required") @Size(min = 1, max = 50, message = "Title must be {min} or {max} of characters") String title,
    @NotBlank(message = "Content is required") String content,
    @NotEmpty(message = "Provide at least 1 category") Set<UUID> categoryId,
    Set<UUID> tagIds) {
}
