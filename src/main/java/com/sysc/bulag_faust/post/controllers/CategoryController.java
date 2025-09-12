package com.sysc.bulag_faust.post.controllers;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.domain.dto.category.CategoryResponse;
import com.sysc.bulag_faust.post.domain.dto.category.CreateCategoryRequest;
import com.sysc.bulag_faust.post.service.category.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("${api.prefix}/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> category = categoryService.getAllCategories();

        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Success", category)
        );
    }

    @PostMapping
    ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
        @Valid CreateCategoryRequest request
    ) {
        CategoryResponse categoryResponse = categoryService.createCategory(
            request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success("Category added successfully", categoryResponse)
        );
    }
}
