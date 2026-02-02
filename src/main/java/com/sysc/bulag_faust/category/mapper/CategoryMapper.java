package com.sysc.bulag_faust.category.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.sysc.bulag_faust.category.Category;
import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.category.dto.CreateCategoryRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {

  Category toEntity(CreateCategoryRequest request);

  List<CategoryResponse> toDtoList(List<Category> categories);

  CategoryResponse toDto(Category category);
}
