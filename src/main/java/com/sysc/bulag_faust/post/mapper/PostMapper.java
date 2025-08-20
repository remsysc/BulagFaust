package com.sysc.bulag_faust.post.mapper;

import com.sysc.bulag_faust.post.dto.PostDTO;
import com.sysc.bulag_faust.post.entities.Post;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDTO toDTO(Post post);
    List<PostDTO> toListDTO(List<Post> post);
}
