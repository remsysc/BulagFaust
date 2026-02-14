package com.sysc.bulag_faust.post.dto;

import java.util.List;

import org.mapstruct.Mapper;

import com.sysc.bulag_faust.category.mapper.CategoryMapper;
import com.sysc.bulag_faust.post.entity.Post;
import com.sysc.bulag_faust.tag.mapper.TagMapper;
import com.sysc.bulag_faust.user.dto.UserMapper;

@Mapper(componentModel = "spring", uses = { CategoryMapper.class, TagMapper.class, UserMapper.class })
public interface PostMapper {

  public List<PostResponse> toResponseList(List<Post> posts);

}
