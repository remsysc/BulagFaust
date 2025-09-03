package com.sysc.bulag_faust.post.service.tag;

import com.sysc.bulag_faust.post.dto.tag.AddTagRequest;
import com.sysc.bulag_faust.post.dto.tag.TagResponse;
import com.sysc.bulag_faust.post.dto.tag.UpdateTagRequest;
import com.sysc.bulag_faust.post.entities.Tag;
import java.util.List;
import java.util.UUID;

public interface TagService {
    TagResponse addTag(AddTagRequest request);
    TagResponse updateTag(UpdateTagRequest request);
    Tag getTagEntityById(UUID id);
    List<TagResponse> getAllTags();

    TagResponse getTagById(UUID id);
}
