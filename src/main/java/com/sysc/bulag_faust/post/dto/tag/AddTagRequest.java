package com.sysc.bulag_faust.post.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddTagRequest {

    @NotBlank
    private String name;
}
