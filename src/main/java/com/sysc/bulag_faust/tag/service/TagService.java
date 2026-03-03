package com.sysc.bulag_faust.tag.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import com.sysc.bulag_faust.core.response.PageResponse;
import com.sysc.bulag_faust.tag.dto.CreateTagRequest;
import com.sysc.bulag_faust.tag.dto.TagResponse;

public interface TagService {

  TagResponse getTagById(UUID id);

  PageResponse<TagResponse> getAllTags(Pageable pageable);

  TagResponse deleteTagById(UUID id);

  TagResponse createTag(CreateTagRequest request);
}
