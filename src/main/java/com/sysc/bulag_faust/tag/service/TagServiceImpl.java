package com.sysc.bulag_faust.tag.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.core.exceptions.base_exception.AlreadyExistException;
import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.tag.Tag;
import com.sysc.bulag_faust.tag.TagRepository;
import com.sysc.bulag_faust.tag.dto.CreateTagRequest;
import com.sysc.bulag_faust.tag.dto.TagResponse;
import com.sysc.bulag_faust.tag.mapper.TagMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;
  private final TagMapper tagMapper;

  @Override
  public TagResponse getTagById(UUID id) {
    return tagRepository.findTagByIdWithPostCount(id).orElseThrow(
        () -> new NotFoundException("Tag", id));

  }

  @Override
  public List<TagResponse> getAllTags() {
    return tagRepository.findAllWithPostCount();
  }

  @Override
  public void deleteTagById(UUID id) {
    if (tagRepository.existsById(id))
      throw new AlreadyExistException("Tag", id);

    tagRepository.deleteById(id);

  }

  @Transactional
  @Override
  public TagResponse createTag(@NonNull CreateTagRequest request) {
    // check if already exist
    if (tagRepository.existsByNameIgnoreCase(request.name()))
      throw new AlreadyExistException("Tag", request.name());

    Tag tag = Tag.builder().name(request.name()).build();
    return tagMapper.toResponse(tagRepository.save(tag));

  }

}
