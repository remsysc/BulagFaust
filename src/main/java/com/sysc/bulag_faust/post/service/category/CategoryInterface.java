package com.sysc.bulag_faust.post.service.category;

import com.sysc.bulag_faust.post.dto.category.AddCategoryRequest;
import com.sysc.bulag_faust.post.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.dto.category.UpdateCategoryRequest;
import com.sysc.bulag_faust.post.entities.Category;
import java.util.List;
import java.util.UUID;

public interface CategoryInterface {
    List<CategoryResponse> getAllCategory();
    Category getCategoryEntityById(UUID id);
    CategoryResponse addCategory(AddCategoryRequest request);
    CategoryResponse updateCategory(UpdateCategoryRequest request);
    void deleteCategory(UUID id);
}
