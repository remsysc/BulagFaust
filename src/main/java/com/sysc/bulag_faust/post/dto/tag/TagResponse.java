package com.sysc.bulag_faust.post.dto.tag;

import java.util.UUID;
import lombok.Data;

@Data
public class TagResponse {

    private UUID id;
    private String name;
}
