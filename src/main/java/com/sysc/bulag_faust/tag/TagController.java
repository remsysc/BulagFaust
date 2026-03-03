package com.sysc.bulag_faust.tag;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.core.response.ApiResponse;
import com.sysc.bulag_faust.core.response.PageResponse;
import com.sysc.bulag_faust.tag.dto.CreateTagRequest;
import com.sysc.bulag_faust.tag.dto.TagResponse;
import com.sysc.bulag_faust.tag.service.TagService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/tags")
public class TagController {

  private final TagService tagService;

  @GetMapping
  public ResponseEntity<ApiResponse<PageResponse<TagResponse>>> getAllTags(
      @PageableDefault(size = 20, direction = Sort.Direction.DESC) Pageable pageable) {
    PageResponse<TagResponse> response = tagService.getAllTags(pageable);
    return ResponseEntity.status(200).body(ApiResponse.success("Retrieved all tags", response));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ApiResponse<TagResponse>> deleteTag(@PathVariable UUID id) {
    TagResponse tag = tagService.deleteTagById(id);
    return ResponseEntity.status(200).body(ApiResponse.success("Tag deleted: " + id, tag));
  }

  @PostMapping
  public ResponseEntity<ApiResponse<TagResponse>> createTag(@Valid @RequestBody CreateTagRequest request) {
    TagResponse tag = tagService.createTag(request);
    return ResponseEntity.status(201).body(ApiResponse.success("Tag created: " + request.name(), tag));
  }
}
