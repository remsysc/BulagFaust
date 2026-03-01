package com.sysc.bulag_faust.tag.service;

import java.util.List;
import java.util.UUID;

import com.sysc.bulag_faust.post.entity.PostStatus;
import com.sysc.bulag_faust.tag.dto.CreateTagRequest;
import com.sysc.bulag_faust.tag.dto.TagResponse;

public interface TagService {

  TagResponse getTagById(UUID id);

  List<TagResponse> getAllTagsWithPostCount(PostStatus status);

  TagResponse deleteTagById(UUID id);

  TagResponse createTag(CreateTagRequest request);
}
