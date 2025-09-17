package com.sysc.bulag_faust.post.controllers;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.post.domain.dto.tag.AddTagRequest;
import com.sysc.bulag_faust.post.domain.dto.tag.TagResponse;
import com.sysc.bulag_faust.post.domain.dto.tag.UpdateTagRequest;
import com.sysc.bulag_faust.post.service.tag.TagService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("${api.prefix}/tag")
public class TagController {

    private final TagService tagService;

    @PostMapping
    @NotNull
    public ResponseEntity<ApiResponse<TagResponse>> addTag(
        @Valid AddTagRequest request
    ) {
        TagResponse tag = tagService.addTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.success(request.getName() + " added successfully", tag)
        );
    }

    @PutMapping
    @NotNull
    public ResponseEntity<ApiResponse<TagResponse>> updateTag(
        @Valid UpdateTagRequest request
    ) {
        TagResponse tag = tagService.updateTag(request);
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Tag updated successfully", tag)
        );
    }

    @GetMapping
    @NotNull
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success(
                "Get all tags successfully",
                tagService.getAllTags()
            )
        );
    }

    @GetMapping("/{id}")
   public ResponseEntity<ApiResponse<TagResponse>> getTagById(
        @Valid @PathVariable UUID id
    ) {
        TagResponse tag = tagService.getTagById(id);

        return ResponseEntity.status(HttpStatus.OK).body(
            ApiResponse.success("Get tag by id successfully", tag)
        );
    }
}
