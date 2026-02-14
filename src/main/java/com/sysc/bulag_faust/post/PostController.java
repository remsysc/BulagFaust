
package com.sysc.bulag_faust.post;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/posts")
public class PostController {

  private final PostService postService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<PostResponse>>> getAllPosts(@RequestParam(required = false) UUID categoryId,
      @RequestParam(required = false) UUID tagId) {

    List<PostResponse> posts = postService.getAllPosts(categoryId, tagId);

    return ResponseEntity.status(200).body(ApiResponse.success("Retrieved all posts", posts));

  }

}
