package com.sysc.bulag_faust.post.mapper;

import com.sysc.bulag_faust.post.dto.post.PostResponse;
import com.sysc.bulag_faust.post.entities.Post;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    //  @Mapping(target = "user", ignore = true)
    PostResponse toDTO(Post post);

    List<PostResponse> toListDTO(List<Post> post);
}
