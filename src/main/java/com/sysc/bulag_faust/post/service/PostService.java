package com.sysc.bulag_faust.post.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.dto.request.CreatePostRequest;
import com.sysc.bulag_faust.post.dto.request.UpdatePostRequest;

public interface PostService {

  Page<PostResponse> getAllPosts(UUID categoryId, UUID tagId, Pageable pageable);

  PostResponse getPostById(UUID id);

  PostResponse createPost(CreatePostRequest request, UUID authorId);

  PostResponse updatePost(UpdatePostRequest request, UUID postId, UUID authorId);
}
