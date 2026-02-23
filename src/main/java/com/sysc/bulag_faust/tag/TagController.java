package com.sysc.bulag_faust.tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sysc.bulag_faust.core.response.ApiResponse;
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
  public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTagsWithPublishedPost() {
    List<TagResponse> tags = tagService.getAllTags();
    return ResponseEntity.status(200).body(ApiResponse.success("Retrieved all tags", tags));
  }

  // @DeleteMapping
  // public ResponseEntity<ApiResponse<TagResponse>>

  @PostMapping
  // TODO: add later along with delete and update
  public ResponseEntity<ApiResponse<TagResponse>> createTag(@Valid @RequestBody CreateTagRequest request) {

    TagResponse tag = tagService.createTag(request);
    return ResponseEntity.status(201).body(ApiResponse.success("Tag created: " + request.name(), tag));

  }
}
