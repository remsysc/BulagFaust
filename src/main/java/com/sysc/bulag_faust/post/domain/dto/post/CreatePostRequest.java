package com.sysc.bulag_faust.post.domain.dto.post;

import lombok.Data;

@Data
public class CreatePostRequest {

    private String title;
    private String content;
    private String categoryName;
}
