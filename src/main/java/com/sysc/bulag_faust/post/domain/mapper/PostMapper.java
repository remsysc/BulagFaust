package com.sysc.bulag_faust.post.domain.mapper;

import com.sysc.bulag_faust.post.domain.dto.post.PostResponse;
import com.sysc.bulag_faust.post.domain.entities.Post;
import com.sysc.bulag_faust.user.entities.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "authorName", source = "author.name")
    @Mapping(target = "categoryName", source = "category.name")
    PostResponse toDTO(Post post);

    List<PostResponse> toListDTO(List<Post> post);

    //null checks for lazy properties
    // not needed with @EntityGraph
    default String mapAuthorName(User author) {
        return author != null ? author.getName() : null;
    }
}
