package com.sysc.bulag_faust.tag.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.tag.TagRepository;
import com.sysc.bulag_faust.tag.dto.TagResponse;

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

  @Override
  public List<TagResponse> getAllTags() {
    return tagRepository.findAllWithPostCount();
  }

  @Override
  public void deleteTagById(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTagById'");
  }

}
