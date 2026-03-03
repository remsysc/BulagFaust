package com.sysc.bulag_faust.tag.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.core.exceptions.base_exception.AlreadyExistException;
import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.core.response.PageResponse;
import com.sysc.bulag_faust.tag.Tag;
import com.sysc.bulag_faust.tag.TagRepository;
import com.sysc.bulag_faust.tag.dto.CreateTagRequest;
import com.sysc.bulag_faust.tag.dto.TagResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  @Override
  public TagResponse getTagById(UUID id) {
    return tagRepository.findTagByIdWithPostCount(id).orElseThrow(
        () -> new NotFoundException("Tag", id));

  }

  @Transactional
  @Override
  public TagResponse deleteTagById(UUID id) {
    if (!tagRepository.existsById(id))
      throw new NotFoundException("Tag", id);

    TagResponse tag = getTagById(id);
    tagRepository.deleteById(id);
    return tag;

  }

  @Transactional
  @Override
  public TagResponse createTag(@NonNull CreateTagRequest request) {
    if (tagRepository.existsByNameIgnoreCase(request.name()))
      throw new AlreadyExistException("Tag", request.name());

    Tag tag = Tag.builder().name(request.name()).build();
    Tag saved = tagRepository.save(tag);
    return new TagResponse(saved.getId(), saved.getName(), 0L);

  }

  @Override
  public PageResponse<TagResponse> getAllTags(Pageable pageable) {
    Page<TagResponse> page = tagRepository.findAllWithPostCount(pageable);
    return PageResponse.from(page);
  }

}
