package com.sysc.bulag_faust.post.dto.category;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AddCategoryRequest {

    @NotEmpty
    private String name;
}
