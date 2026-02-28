package com.sysc.bulag_faust.post.dto;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.sysc.bulag_faust.category.Category;
import com.sysc.bulag_faust.post.entity.Post;
import com.sysc.bulag_faust.tag.Tag;

@Mapper(componentModel = "spring")
public interface PostMapper {

  List<PostResponse> toResponses(List<Post> posts);

  @Mapping(source = "author.username", target = "author")
  @Mapping(source = "categories", target = "categories")
  PostResponse toResponse(Post post);

  default String categoryToString(Category category) {
    return category.getName();
  }

  default String tagToString(Tag tag) {
    return tag.getName();
  }
}
