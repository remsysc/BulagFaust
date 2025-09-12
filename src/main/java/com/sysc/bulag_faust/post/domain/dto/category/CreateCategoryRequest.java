package com.sysc.bulag_faust.post.domain.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCategoryRequest {

    @NotEmpty
    private String name;
}
