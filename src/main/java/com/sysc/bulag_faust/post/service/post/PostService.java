package com.sysc.bulag_faust.post.service.post;

import com.sysc.bulag_faust.post.domain.dto.post.CreatePostRequest;
import com.sysc.bulag_faust.post.domain.dto.post.PostResponse;
import com.sysc.bulag_faust.post.domain.dto.post.UpdatePostRequest;
import com.sysc.bulag_faust.post.domain.entities.Post;
import com.sysc.bulag_faust.post.domain.entities.PostStatus;
import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponse createPost(CreatePostRequest addRequestDTO, UUID userId);
    PostResponse updatePost(UpdatePostRequest request);
    Post getPostEntityById(UUID id);
    void deletePost(UUID id);
    List<PostResponse> getAllPostsByAuthorId(UUID userId);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByStatus(PostStatus status);
    PostResponse getPostWithAuthorId(UUID id);
}
