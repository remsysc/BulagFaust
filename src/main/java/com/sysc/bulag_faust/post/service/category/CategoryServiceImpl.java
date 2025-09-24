package com.sysc.bulag_faust.post.service.category;

import com.sysc.bulag_faust.core.exception.category.CategoryAlreadyExist;
import com.sysc.bulag_faust.core.exception.category.CategoryNotFound;
import com.sysc.bulag_faust.post.domain.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.domain.dto.category.CreateCategoryRequest;
import com.sysc.bulag_faust.post.domain.dto.category.UpdateCategoryRequest;
import com.sysc.bulag_faust.post.domain.entities.Category;
import com.sysc.bulag_faust.post.domain.mapper.CategoryMapper;
import com.sysc.bulag_faust.post.repository.CategoryRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryResponse> getAllCategoriesWithPosts() {
        List<Category> categories = categoryRepository.findByPostsIsNotEmpty();
        return categoryMapper.toDtoList(categories);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(
        @NotNull CreateCategoryRequest request
    ) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new CategoryAlreadyExist(request.getName());
        }
        Category category = new Category(request.getName());
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(
        @NotNull UpdateCategoryRequest request
    ) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new CategoryAlreadyExist(request.getName());
        }

        Category category = getCategoryEntityById(request.getId());
        return categoryMapper.toDto(category); // get will return the actual value if its optional
    }

    @Override
    public Category getCategoryEntityById(UUID id) {
        return categoryRepository
            .findById(id)
            .orElseThrow(() -> new CategoryNotFound(id.toString()));
    }

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Category category = getCategoryEntityById(id);
        if (!category.getPosts().isEmpty()) throw new IllegalStateException(
            "Category has posts associated with it"
        );
        categoryRepository.delete(category);
    }
}
