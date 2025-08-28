package com.sysc.bulag_faust.post.dto.category;

import com.sysc.bulag_faust.post.dto.post.PostResponse;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CategoryResponse {

    private UUID id;
    private String name;
    List<PostResponse> posts;
}
