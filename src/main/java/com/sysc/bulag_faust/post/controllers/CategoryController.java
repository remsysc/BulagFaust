package com.sysc.bulag_faust.post.controllers;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.domain.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.domain.dto.category.CreateCategoryRequest;
import com.sysc.bulag_faust.post.service.category.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("${api.prefix}/category")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  @NotNull
  public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
    List<CategoryResponse> category = categoryService.getAllCategories();

    return ResponseEntity.status(HttpStatus.OK).body(
        ApiResponse.success("Success", category));
  }

  @PostMapping
  @NotNull
  public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
      @Valid @RequestBody CreateCategoryRequest request) {
    CategoryResponse categoryResponse = categoryService.createCategory(
        request);
    return ResponseEntity.status(HttpStatus.CREATED).body(
        ApiResponse.success("Category added successfully", categoryResponse));
  }

  @DeleteMapping("/{id}")
  @NotNull

  public ResponseEntity<ApiResponse<CategoryResponse>> deleteCategory(
      @Valid @PathVariable UUID id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(
        ApiResponse.success("Category deleted successfully", null));
  }
}
