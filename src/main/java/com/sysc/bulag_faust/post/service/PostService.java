package com.sysc.bulag_faust.post.service;

import java.util.List;
import java.util.UUID;

import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.dto.request.CreatePostRequest;

public interface PostService {

  List<PostResponse> getAllPosts(UUID categoryId, UUID tagId);

  PostResponse getPostById(UUID id);

  PostResponse createPost(CreatePostRequest request, UUID authorId);
}
