package com.sysc.bulag_faust.post;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sysc.bulag_faust.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

  @Query(value = """
      SELECT p.id FROM Post p
      WHERE (:categoryId IS NULL OR EXISTS(
          SELECT 1 FROM p.categories c WHERE c.id = :categoryId))
      AND (:tagId IS NULL OR EXISTS(
          SELECT 1 FROM p.tags t WHERE t.id = :tagId))
      """, countQuery = """
      SELECT COUNT(p) FROM Post p
      WHERE (:categoryId IS NULL OR EXISTS(
          SELECT 1 FROM p.categories c WHERE c.id = :categoryId))
      AND (:tagId IS NULL OR EXISTS(
          SELECT 1 FROM p.tags t WHERE t.id = :tagId))
      """)
  Page<UUID> findPostIds(
      @Param("categoryId") UUID categoryId,
      @Param("tagId") UUID tagId,
      Pageable pageable);

  // Query 2: fetch full data for just those IDs (with JOIN FETCH, no pagination)
  @Query("""
          SELECT DISTINCT p FROM Post p
          JOIN FETCH p.author
          LEFT JOIN FETCH p.categories
          LEFT JOIN FETCH p.tags
          WHERE p.id IN :ids
      """)
  List<Post> findAllByIdIn(@Param("ids") List<UUID> ids);

  @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Post p WHERE p.id = :postId AND p.author.id = :authorId")
  boolean existsByIdAndAuthorId(@Param("postId") UUID postId, @Param("authorId") UUID authorId);

}
