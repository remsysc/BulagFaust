package com.sysc.bulag_faust.post.dto.request;

import java.util.Set;
import java.util.UUID;

public record CreateDraftRequest(
    String title, String content, Set<String> tagNames, Set<UUID> categoryIds) {

  // validation and null safety

  public CreateDraftRequest {
    categoryIds = categoryIds != null ? categoryIds : Set.of();
    tagNames = tagNames != null ? tagNames : Set.of();
  }
}
