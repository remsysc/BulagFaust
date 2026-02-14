package com.sysc.bulag_faust.post;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sysc.bulag_faust.post.dto.PostResponse;
import com.sysc.bulag_faust.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

  @Query("""
      SELECT DISTINCT p FROM Post p JOIN p.categories c JOIN p.tags tags
      WHERE (:categoryId IS NULL OR  c.id = :categoryId) AND(:tagId IS NULL OR t.id = :tagId)
      """)
  List<PostResponse> findAllByCategoryIdAndTagId(UUID categoryId, UUID tagId);

}
