package com.sysc.bulag_faust.post.service;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;
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

  @Transactional
  @Override
  public PostResponse createDraft(@NotNull CreateDraftRequest request, UUID authorId) {

    User author = userRepository.getReferenceById(authorId);

    Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.categoryIds()));
    Set<Tag> tags = new HashSet<>(tagRepository.findAllById(request.tagIds()));

    EntityValidationUtils.vaidateAllFound(request.categoryIds(), categories, Category::getId,
        "Category");
    EntityValidationUtils.vaidateAllFound(request.tagIds(), tags, Tag::getId, "Tag");

    Post post = Post.builder().author(author)
        .title(request.title())
        .content(request.content())
        .status(PostStatus.DRAFT)
        .categories(categories)
        .tags(tags)
        .build();

    return postMapper.toResponse(postRepository.save(post));

  }

}
