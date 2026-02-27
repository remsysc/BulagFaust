package com.sysc.bulag_faust.post.service;

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
import com.sysc.bulag_faust.post.PostRepository;
import com.sysc.bulag_faust.post.dto.PostMapper;
import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.dto.request.CreateDraftRequest;
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

  public static <T> void findInvalidId(T category, T request) {
    if (category.size() != request.categoryIds().size()) {

      Set<UUID> foundIds = category.stream()
          .map(Category::getId)
          .collect(Collectors.toSet());

      Set<UUID> missingIds = request.categoryIds()
          .stream()
          .filter(id -> !foundIds.contains(id))
          .collect(Collectors.toSet());

      throw new NotFoundException("Category", missingIds);
    }
  }

  @Override
  public PostResponse createDraft(@NotNull CreateDraftRequest request, UUID authorId) {

    User author = userRepository.getReferenceById(authorId);

    Set<Category> category = new HashSet<>(categoryRepository.findAllById(request.categoryIds()));

    if (category.size() != request.categoryIds().size()) {

      Set<UUID> foundIds = category.stream()
          .map(Category::getId)
          .collect(Collectors.toSet());

      Set<UUID> missingIds = request.categoryIds()
          .stream()
          .filter(id -> !foundIds.contains(id))
          .collect(Collectors.toSet());

      throw new NotFoundException("Category", missingIds);
    }

    Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.tagIds()));

    Post post = Post.builder().author(author)
        .title(request.title())
        .content(request.content())
        .status(PostStatus.DRAFT)
        .categories(category)
        .tags(tags)
        .build();

  }

}
