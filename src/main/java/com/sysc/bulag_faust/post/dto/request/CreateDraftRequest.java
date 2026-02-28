package com.sysc.bulag_faust.post.dto.request;

import java.util.Set;
import java.util.UUID;

public record CreateDraftRequest(
    String title, String content, Set<UUID> tagIds, Set<UUID> categoryIds) {

  private static final String DEFAULT_TITLE = "Untitled";
  private static final String DEFAULT_CONTENT = "";

  public CreateDraftRequest {
    title = (title != null && !title.isBlank()) ? title : DEFAULT_TITLE;
    content = content != null ? content : DEFAULT_CONTENT;
    tagIds = tagIds != null ? tagIds : Set.of();
    categoryIds = categoryIds != null ? categoryIds : Set.of();
  }
}
