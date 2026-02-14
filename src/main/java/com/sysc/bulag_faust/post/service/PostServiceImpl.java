package com.sysc.bulag_faust.post.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.post.PostRepository;
import com.sysc.bulag_faust.post.dto.PostMapper;
import com.sysc.bulag_faust.post.dto.PostResponse;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;

  public List<PostResponse> getAllPosts(UUID categoryId, UUID tagId) {

    return postMapper.toResponseList(postRepository.findAllByCategoryIdAndTagId(categoryId, tagId));
  }

}
