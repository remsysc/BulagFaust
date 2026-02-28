
package com.sysc.bulag_faust.post;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.core.utils.AuthUtils;
import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.dto.request.CreatePostRequest;
import com.sysc.bulag_faust.post.service.PostService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {

  private final PostService postService;
  private final AuthUtils authUtils;

  @GetMapping
  public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(@RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId) {
    List<PostResponse> posts = postService.getAllPosts(categoryId, tagId);
    return ResponseEntity.status(200).body(ApiResponse.success("Retrieved all posts", posts));
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

  // TODO: add CRUD

}
