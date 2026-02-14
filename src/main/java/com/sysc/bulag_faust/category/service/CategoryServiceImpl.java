package com.sysc.bulag_faust.category.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.category.Category;
import com.sysc.bulag_faust.category.CategoryRepository;
import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.category.dto.CreateCategoryRequest;
import com.sysc.bulag_faust.category.mapper.CategoryCountDto;
import com.sysc.bulag_faust.category.mapper.CategoryMapper;
import com.sysc.bulag_faust.core.exceptions.base_exception.AlreadyExistException;
import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.post.PostRepository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public List<CategoryCountDto> getAllCategoriesWithCounts() {

    return categoryRepository.findAllWithPublishedPostCounts();

  }

  @Transactional
  @Override
  public CategoryResponse createCategory(@NonNull CreateCategoryRequest request) {

    if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
      throw new AlreadyExistException("Category: " + request.getName() + " already exist");
    }

    Category category = categoryMapper.toEntity(request);
    return categoryMapper.toDto(categoryRepository.save(category));

  }

  @Transactional
  @Override
  public CategoryResponse updateCategory(@NonNull UUID id, @NonNull CreateCategoryRequest request) {
    Category existing = getCategoryEntityById(id);
    if (categoryRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
      throw new AlreadyExistException("Category: " + request.getName() + " already exist");
    }

    Category updated = existing.toBuilder().name(request.getName()).build();
    return categoryMapper.toDto(categoryRepository.save(updated));

  }

  private Category getCategoryEntityById(@NonNull UUID id) {

    return categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("ID: " + id.toString() + " not found"));
  }

  @Transactional
  @Override
  public void deleteCategory(@NonNull UUID id) {

    // check if theres a post associated with it
    // TEST: test if working
    if (categoryRepository.existsByIdAndPostsIsNotEmpty(id)) {
      throw new IllegalStateException("Category has posts associated with it");
    }
    categoryRepository.deleteById(id);
  }

}
