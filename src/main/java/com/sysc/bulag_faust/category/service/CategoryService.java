package com.sysc.bulag_faust.category.service;

import java.util.List;
import java.util.UUID;

import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.category.dto.CreateCategoryRequest;

public interface CategoryService {

  List<CategoryResponse> getAllCategoriesWithCounts();

  CategoryResponse createCategory(CreateCategoryRequest request);

  CategoryResponse updateCategory(UUID id, CreateCategoryRequest request);

  void deleteCategory(UUID id);

}
