package com.sysc.bulag_faust.post.domain.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddTagRequest {

    @NotBlank
    private String name;
}
