package com.sysc.bulag_faust.post.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class PostDTO {

    private UUID id;
    private String title;
    private String content;

    private String status;

    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;
}
