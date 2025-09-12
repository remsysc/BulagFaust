package com.sysc.bulag_faust.post.service.category;

import com.sysc.bulag_faust.post.domain.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.domain.dto.category.CreateCategoryRequest;
import com.sysc.bulag_faust.post.domain.dto.category.UpdateCategoryRequest;
import com.sysc.bulag_faust.post.domain.entities.Category;
import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> getAllCategoriesWithPosts();
    List<CategoryResponse> getAllCategories();
    Category getCategoryEntityById(UUID id);
    CategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse updateCategory(UpdateCategoryRequest request);
    void deleteCategory(UUID id);
}
