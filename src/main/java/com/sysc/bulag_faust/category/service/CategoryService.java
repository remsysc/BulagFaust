package com.sysc.bulag_faust.category.service;

import java.util.List;
import java.util.UUID;

import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.category.dto.CreateCategoryRequest;
import com.sysc.bulag_faust.category.mapper.CategoryCountDto;

public interface CategoryService {

  List<CategoryCountDto> getAllCategoriesWithCounts();

  CategoryResponse createCategory(CreateCategoryRequest request);

  CategoryResponse updateCategory(UUID id, CreateCategoryRequest request);

  CategoryResponse deleteCategoryById(UUID id);

}
