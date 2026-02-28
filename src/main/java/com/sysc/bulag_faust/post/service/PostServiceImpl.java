package com.sysc.bulag_faust.post.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysc.bulag_faust.category.Category;
import com.sysc.bulag_faust.category.CategoryRepository;
import com.sysc.bulag_faust.core.exceptions.base_exception.NotFoundException;
import com.sysc.bulag_faust.core.utils.EntityValidationUtils;
import com.sysc.bulag_faust.post.PostRepository;
import com.sysc.bulag_faust.post.dto.PostMapper;
import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.dto.request.CreatePostRequest;
import com.sysc.bulag_faust.post.entity.Post;
import com.sysc.bulag_faust.post.entity.PostStatus;
import com.sysc.bulag_faust.tag.Tag;
import com.sysc.bulag_faust.tag.TagRepository;
import com.sysc.bulag_faust.user.User;
import com.sysc.bulag_faust.user.UserRepository;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PostMapper postMapper;
  private final UserRepository userRepository;
  private final CategoryRepository categoryRepository;
  private final TagRepository tagRepository;

  public List<PostResponse> getAllPosts(UUID categoryId, UUID tagId) {

    return postMapper.toResponses(postRepository.findAllByCategoryIdAndTagId(categoryId, tagId));
  }

  @Override
  public PostResponse getPostById(UUID id) {
    Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("Post", id));
    return postMapper.toResponse(post);
  }

  @Transactional
  @Override
  public PostResponse createPost(@NotNull CreatePostRequest request, UUID authorId) {

    User author = userRepository.getReferenceById(authorId);

    Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryIds()));
    Set<Tag> tags = resolveOrCreateTags(request.tagNames());
    EntityValidationUtils.vaidateAllFound(request.categoryIds(), categories, Category::getId,
        "Category");

    if (request.status() == PostStatus.PUBLISHED) {
      validateForPublish(request);
    }

    Post post = Post.builder().author(author)
        .title(request.title())
        .content(request.content())
        .status(request.status())
        .categories(categories)
        .tags(tags)
        .build();

    return postMapper.toResponse(postRepository.save(post));

  }

  private Set<Tag> resolveOrCreateTags(Set<String> tagNames) {
    return tagNames.stream()
        .map(name -> tagRepository.findByName(name.toLowerCase().trim())
            .orElseGet(() -> tagRepository.save(
                Tag.builder().name(name).build())))
        .collect(Collectors.toSet());
  }

  // TODO: add groupValidation
  private void validateForPublish(CreatePostRequest request) {
    List<String> errors = new ArrayList<>();

    if (request.title() == null || request.title().isBlank()) {
      errors.add("Title is required to publish");
    }
    if (request.content() == null || request.content().isBlank()) {
      errors.add("Content is required to publish");
    }
    if (request.categoryIds() == null || request.categoryIds().isEmpty()) {
      errors.add("At least one category is required to publish");
    }

    if (!errors.isEmpty()) {
      throw new IllegalArgumentException(String.join(", ", errors));
      // your GlobalExceptionHandler already handles this ✅
    }
  }

}
