package com.sysc.bulag_faust.post.controllers;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.dto.post.AddPostRequest;
import com.sysc.bulag_faust.post.dto.post.PostResponse;
import com.sysc.bulag_faust.post.service.post.PostService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/post")
public class PostControllers {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPosts() {
        List<PostResponse> post = postService.getAllPosts();
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("getPosts success: ", post)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<List<PostResponse>>> getPostByUser(
        @Valid @PathVariable UUID userId
    ) {
        List<PostResponse> post = postService.getAllPostsByUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Get all post by user is successful: ", post)
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
        @Valid AddPostRequest request
    ) {
        PostResponse postDTO = postService.addPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success("Post created: ", postDTO)
        );
    }
}
