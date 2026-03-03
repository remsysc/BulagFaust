package com.sysc.bulag_faust.category.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.category.dto.CreateCategoryRequest;
import com.sysc.bulag_faust.core.response.PageResponse;

public interface CategoryService {

  CategoryResponse createCategory(CreateCategoryRequest request);

  CategoryResponse updateCategory(UUID id, CreateCategoryRequest request);

  CategoryResponse deleteCategoryById(UUID id);

  PageResponse<CategoryResponse> getAllCategories(Pageable pageable);

}
