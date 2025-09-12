package com.sysc.bulag_faust.post.domain.dto.category;

import jakarta.validation.constraints.NotBlank;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateCategoryRequest {

    @NotBlank
    UUID id;

    @NotBlank
    String name;
}
