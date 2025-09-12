package com.sysc.bulag_faust.post.domain.dto.post;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

    private UUID id;
    private String title;
    private String content;

    private String status;

    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;

    private String authorName;
    private String categoryName;
}
