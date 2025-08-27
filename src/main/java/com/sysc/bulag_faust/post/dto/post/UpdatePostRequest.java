package com.sysc.bulag_faust.post.dto.post;

import java.util.UUID;
import lombok.Data;

@Data
public class UpdatePostRequest {

    private UUID id;
    private String title;
    private String content;
}
