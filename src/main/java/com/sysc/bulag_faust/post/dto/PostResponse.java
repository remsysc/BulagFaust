package com.sysc.bulag_faust.post.dto;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.post.entity.PostStatus;
import com.sysc.bulag_faust.tag.dto.TagResponse;
import com.sysc.bulag_faust.user.dto.UserResponse;

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
  private UserResponse author;
  private String title;
  private String content;
  private PostStatus status;
  private Integer readingTime;
  private Set<CategoryResponse> categories;// TODO: CREATE CUSTOM RESPONSE WITHOUT COUNT
  private Set<TagResponse> tags;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
