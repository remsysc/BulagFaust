package com.sysc.bulag_faust.post.mapper;

import com.sysc.bulag_faust.post.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.entities.Category;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { PostMapper.class })
public interface CategoryMapper {
    CategoryResponse toDto(Category category);
    List<CategoryResponse> toDtoList(List<Category> category);
}
