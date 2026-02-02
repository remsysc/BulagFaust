package com.sysc.bulag_faust.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryRequest {

  @NotBlank(message = "Category name is required")
  @Size(min = 3, max = 50, message = "Category name must be {min} and {max} characters")
  @Pattern(regexp = "^[\\w\\s-]+$", message = "Category name  can only contain letter, numbers, spaces, and hyphens")
  private String name;

}
