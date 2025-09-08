package com.sysc.bulag_faust.post.service.post;

import com.sysc.bulag_faust.post.dto.post.AddPostRequest;
import com.sysc.bulag_faust.post.dto.post.PostResponse;
import com.sysc.bulag_faust.post.dto.post.UpdatePostRequest;
import com.sysc.bulag_faust.post.entities.Post;
import com.sysc.bulag_faust.post.entities.PostStatus;
import java.util.List;
import java.util.UUID;

public interface PostService {
    PostResponse addPost(AddPostRequest addRequestDTO, UUID userId);
    PostResponse updatePost(UpdatePostRequest request);
    Post getPostEntityById(UUID id);
    void deletePost(UUID id);
    List<PostResponse> getAllPostsByAuthorIdWithAuthor(UUID userId);
    List<PostResponse> getAllPosts();
    List<PostResponse> getPostsByStatus(PostStatus status);
    List<PostResponse> getAllPostWithAuthors();
    PostResponse getPostWithAuthor(UUID id);
}
