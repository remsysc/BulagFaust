
package com.sysc.bulag_faust.post;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.core.response.PageResponse;
import com.sysc.bulag_faust.core.utils.AuthUtils;
import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.dto.request.CreatePostRequest;
import com.sysc.bulag_faust.post.dto.request.UpdatePostRequest;
import com.sysc.bulag_faust.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {

  private final PostService postService;
  private final AuthUtils authUtils;

  // PostController.java
  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getAllPosts(
      @RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId,
      @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {

    Page<PostResponse> posts = postService.getAllPosts(categoryId, tagId, pageable);
    return ResponseEntity.ok(ApiResponse.success("Retrieved all posts", PageResponse.from(posts)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse<PostResponse>> getPostById(@PathVariable UUID id) {
    PostResponse post = postService.getPostById(id);
    return ResponseEntity.status(200).body(ApiResponse.success("Retrieved a post", post));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<PostResponse>> createPost(@Valid @RequestBody CreatePostRequest request) {
    PostResponse post = postService.createPost(request, authUtils.getAuthenticateUserID());
    return ResponseEntity.status(201).body(ApiResponse.success("Created a post", post));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable UUID id,
      @Valid @RequestBody UpdatePostRequest request) {
    PostResponse response = postService.updatePost(request, id, authUtils.getAuthenticateUserID());
    return ResponseEntity.status(200).body(ApiResponse.success("Post updated", response));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<PostResponse>> deletePost(@PathVariable UUID id) {

    PostResponse response = postService.deletePost(id, authUtils.getAuthenticateUserID());
    return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success("Post deleted", response));
  }

}
