package com.sysc.bulag_faust.post.dto.post;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class PostResponse {

    private UUID id;
    private String title;
    private String content;

    private String status;

    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;
}
