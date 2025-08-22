package com.sysc.bulag_faust.post.mapper;

import com.sysc.bulag_faust.post.dto.PostDTO;
import com.sysc.bulag_faust.post.entities.Post;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    //  @Mapping(target = "user", ignore = true)
    PostDTO toDTO(Post post);

    List<PostDTO> toListDTO(List<Post> post);
}
