package com.sysc.bulag_faust.post.dto.category;

import java.util.UUID;
import lombok.Data;

@Data
public class CategoryResponse {

    private UUID id;
    private String name;
}
