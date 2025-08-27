package com.sysc.bulag_faust.post.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddCategoryRequest {

    @NotBlank
    private String name;
}
