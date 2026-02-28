package com.sysc.bulag_faust.post.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.sysc.bulag_faust.post.entity.PostStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {
  private UUID id;
  private String author;
  private String title;
  private String content;
  private PostStatus status;
  private Integer readingTime;
  private Set<String> categories;// TODO: CREATE CUSTOM RESPONSE WITHOUT COUNT
  private Set<String> tags;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
