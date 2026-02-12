package com.sysc.bulag_faust.tag.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.tag.TagRepository;
import com.sysc.bulag_faust.tag.dto.TagResponse;
import com.sysc.bulag_faust.tag.mapper.TagMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TagServiceImpl implements TagService {

  private final TagRepository tagRepository;

  private final TagMapper tagMapper;

  @Override
  public TagResponse getTagById(UUID id) {

    return tagRepository.findTagByIdWithPostCount(id).orElseThrow(
        () -> new NotFoundException("Tag with id: " + id + "not found"));

  }

  @Override
  public List<TagResponse> getAllTags() {
    return tagMapper.toResponses(tagRepository.findAll());
  }

  @Override
  public void deleteTagById(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTagById'");
  }

}
