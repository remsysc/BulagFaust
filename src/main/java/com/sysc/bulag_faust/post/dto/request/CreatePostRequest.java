package com.sysc.bulag_faust.post.dto.request;

import java.util.Set;
import java.util.UUID;

import com.sysc.bulag_faust.post.entity.PostStatus;

import jakarta.validation.constraints.NotNull;

public record CreatePostRequest(
    String title, String content, @NotNull PostStatus status, Set<String> tagNames,
    Set<UUID> categoryIds) {

  // validation and null safety

  public CreatePostRequest {
    categoryIds = categoryIds != null ? categoryIds : Set.of();
    tagNames = tagNames != null ? tagNames : Set.of();
  }
}
