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
      throw new AlreadyExistException("Category", request.getName());
    }

    Category category = categoryMapper.toEntity(request);
    return categoryMapper.toDto(categoryRepository.save(category));

  }

  @Transactional
  @Override
  public CategoryResponse updateCategory(@NonNull UUID id, @NonNull CreateCategoryRequest request) {
    if (categoryRepository.existsByNameIgnoreCaseAndIdNot(request.getName(), id)) {
      throw new AlreadyExistException("Category", id);
    }
    Category existing = getCategoryEntityById(id);

    existing.updateName(request.getName());
    return categoryMapper.toDto(existing);

  }

  private Category getCategoryEntityById(@NonNull UUID id) {

    return categoryRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Category", id));
  }

  @Transactional
  @Override
  public CategoryResponse deleteCategoryById(@NonNull UUID id) {

    if (categoryRepository.existsByIdAndPostsIsNotEmpty(id)) {
      throw new IllegalStateException("Category has posts associated with it");
    }
    CategoryResponse categoryResponse = categoryMapper.toDto(getCategoryEntityById(id));
    categoryRepository.deleteById(id);
    return categoryResponse;
  }

}
