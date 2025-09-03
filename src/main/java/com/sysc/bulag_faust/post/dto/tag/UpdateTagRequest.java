package com.sysc.bulag_faust.post.dto.tag;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.UUID;
import lombok.Data;

@Data
public class UpdateTagRequest {

    @NotEmpty
    private UUID id;

    @NotBlank
    private String name;
}
