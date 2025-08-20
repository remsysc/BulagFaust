package com.sysc.bulag_faust.post.dto.request;

import java.util.UUID;
import lombok.Data;

@Data
public class AddRequestDTO {

    private String title;
    private String content;

    private UUID userId;
}
