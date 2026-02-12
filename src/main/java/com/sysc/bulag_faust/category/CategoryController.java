package com.sysc.bulag_faust.category;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.category.dto.CreateCategoryRequest;
import com.sysc.bulag_faust.category.service.CategoryService;
import com.sysc.bulag_faust.core.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategoriesWithPublishedCount() {

    List<CategoryResponse> category = categoryService.getAllCategoriesWithCounts();

    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Categories retrieved", category));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
      @Valid @RequestBody CreateCategoryRequest request) {
    CategoryResponse categoryResponse = categoryService.createCategory(request);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ApiResponse.success("Category: " + categoryResponse.getName() + " added", categoryResponse));

  }

  @PutMapping("/{id}")
  public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable UUID id,
      @Valid @RequestBody CreateCategoryRequest request) {
    CategoryResponse categoryResponse = categoryService.updateCategory(id, request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(ApiResponse.success("Category: " + request.getName() + " updated", categoryResponse));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<CategoryResponse>> deleteCategory(@PathVariable UUID id) {
    categoryService.deleteCategory(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ApiResponse.success("Deleted: " + id.toString(), null));
  }

}
