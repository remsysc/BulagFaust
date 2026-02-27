package com.sysc.bulag_faust.post.dto.request;

import java.util.Set;
import java.util.UUID;

public record CreateDraftRequest(
    String title, String content, Set<UUID> tagIds, Set<UUID> categoryIds) {
}
