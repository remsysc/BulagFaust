package com.sysc.bulag_faust.post.service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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
import com.sysc.bulag_faust.post.dto.request.UpdatePostRequest;
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

  @Override
  public Page<PostResponse> getAllPosts(UUID categoryId, UUID tagId, Pageable pageable) {
    Page<UUID> idPage = postRepository.findPostIds(categoryId, tagId, pageable);

    List<Post> posts = postRepository.findAllByIdIn(idPage.getContent());

    return new PageImpl<>(
        postMapper.toResponses(posts),
        pageable,
        idPage.getTotalElements());
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
    Post post = Post.builder().author(author)
        .title(request.title())
        .content(request.content())
        .status(request.status())
        .build();
    if (request.status() == PostStatus.PUBLISHED) {
      Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryIds()));
      Set<Tag> tags = resolveOrCreateTags(request.tagNames());

      EntityValidationUtils.vaidateAllFound(request.categoryIds(), categories, Category::getId,
          "Category");
      post.assignCategories(categories);
      post.assignTags(tags);
      post.publish();
    }
    return postMapper.toResponse(postRepository.save(post));
  }

  @Transactional
  @Override
  public PostResponse updatePost(@NotNull UpdatePostRequest request, UUID postId, UUID authorId) {
    Post post = postRepository.findById(postId).orElseThrow(
        () -> new NotFoundException("Post", postId));
    if (!postRepository.existsByIdAndAuthorId(postId, authorId)) {
      throw new AccessDeniedException("Access Denied");
    }

    if (request.categoryIds() != null && !request.categoryIds().isEmpty()) {
      Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryIds()));
      EntityValidationUtils.vaidateAllFound(request.categoryIds(),
          categories, Category::getId, "Category");
      post.assignCategories(categories);
    }
    if (request.tagNames() != null && !request.tagNames().isEmpty()) {
      Set<Tag> tags = resolveOrCreateTags(request.tagNames());
      post.assignTags(tags);
    }

    post.updates(request.title(), request.content(), request.status());
    return postMapper.toResponse(post);

  }

  private Set<Tag> resolveOrCreateTags(Set<String> tagNames) {
    Set<String> normalized = tagNames.stream()
        .map(name -> name.toLowerCase().trim())
        .collect(Collectors.toSet());

    // 1 SELECT for all existing tags
    List<Tag> existing = tagRepository.findAllByNameIn(normalized);
    Set<String> existingNames = existing.stream()
        .map(Tag::getName)
        .collect(Collectors.toSet());

    // Only INSERT the ones that don't exist
    List<Tag> newTags = normalized.stream()
        .filter(name -> !existingNames.contains(name))
        .map(name -> Tag.builder().name(name).build())
        .collect(Collectors.toList());

    if (!newTags.isEmpty()) {
      existing.addAll(tagRepository.saveAll(newTags)); // 1 batch INSERT
    }

    return new HashSet<>(existing);
  }

  @Transactional
  @Override
  public PostResponse deletePost(UUID postId, UUID authorId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new NotFoundException("Post", postId));

    if (!post.getAuthor().getId().equals(authorId)) {
      throw new AccessDeniedException("You can only delete your own posts");
    }

    postRepository.delete(post);
    return postMapper.toResponse(post);
  }

  // TODO: add groupValidation

}
