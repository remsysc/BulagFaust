package com.sysc.bulag_faust.post.dto.request;

import java.util.Set;
import java.util.UUID;

import com.sysc.bulag_faust.post.entity.PostStatus;

import jakarta.validation.constraints.Size;

public record UpdatePostRequest(

    @Size(max = 200) String title,
    @Size(max = 100000) String content,
    PostStatus status, Set<String> tagNames,
    Set<UUID> categoryIds) {

}
