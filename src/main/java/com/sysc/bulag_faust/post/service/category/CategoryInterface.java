package com.sysc.bulag_faust.post.service.category;

import com.sysc.bulag_faust.post.dto.category.AddCategoryRequest;
import com.sysc.bulag_faust.post.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.dto.category.UpdateCategoryRequest;
import java.util.UUID;

public interface CategoryInterface {
    CategoryResponse addCategory(AddCategoryRequest request);
    CategoryResponse updateCategory(UpdateCategoryRequest request);
    CategoryResponse deleteCategory(UUID id);
}
