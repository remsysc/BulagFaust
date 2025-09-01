package com.sysc.bulag_faust.post.service.category;

import com.sysc.bulag_faust.core.exception.post.CategoryAlreadyExist;
import com.sysc.bulag_faust.core.exception.post.CategoryNotFound;
import com.sysc.bulag_faust.post.dto.category.AddCategoryRequest;
import com.sysc.bulag_faust.post.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.dto.category.UpdateCategoryRequest;
import com.sysc.bulag_faust.post.entities.Category;
import com.sysc.bulag_faust.post.mapper.CategoryMapper;
import com.sysc.bulag_faust.post.repository.CategoryRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();

        return categoryMapper.toDtoList(categories);
    }

    @Override
    public CategoryResponse addCategory(AddCategoryRequest request) {
        if (categoryRepository.findByName(request.getName()) != null) {
            throw new CategoryAlreadyExist(request.getName());
        }
        Category category = new Category(request.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(UpdateCategoryRequest request) {
        if (categoryRepository.existsByName(request.getName())) {
            throw new CategoryAlreadyExist(request.getName());
        }

        Category category = getCategoryEntityById(request.getId());
        return categoryMapper.toDto(category); // get will return the actual value if its optional
    }

    @Override
    public Category getCategoryEntityById(UUID id) {
        Category category = categoryRepository
            .findById(id)
            .orElseThrow(() -> new CategoryNotFound(id.toString()));
        return category;
    }

    @Override
    public void deleteCategory(UUID id) {
        categoryRepository.delete(getCategoryEntityById(id));
    }
}
