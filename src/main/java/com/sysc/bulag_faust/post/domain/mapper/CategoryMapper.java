package com.sysc.bulag_faust.post.domain.mapper;

import com.sysc.bulag_faust.post.domain.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.domain.entities.Category;
import com.sysc.bulag_faust.post.domain.entities.Post;
import com.sysc.bulag_faust.post.domain.entities.PostStatus;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

//Mapper handles transformation
@Mapper(componentModel = "spring", uses = { PostMapper.class })
public interface CategoryMapper {
    @Mapping(
        target = "postCount",
        source = "posts",
        qualifiedByName = "calculatePublishedPostCount"
    )
    CategoryResponse toDto(Category category);

    List<CategoryResponse> toDtoList(List<Category> category);

    @Named("calculatePublishedPostCount")
    default long calculatePublishedPostCount(List<Post> posts) {
        return calculatePostCount(posts, PostStatus.PUBLISHED);
    }

    @Named("calculateDraftPostCount")
    default long calculateDraftPostCount(List<Post> posts) {
        return calculatePostCount(posts, PostStatus.DRAFT);
    }

    @Named("calculatePostsByStatus")
    default long calculatePostCount(List<Post> posts, PostStatus status) {
        if (posts == null) {
            return 0;
        }

        return posts
            .stream()
            .filter(post -> status.equals(post.getStatus()))
            .count();
    }
}
