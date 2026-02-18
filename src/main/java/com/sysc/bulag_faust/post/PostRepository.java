package com.sysc.bulag_faust.post;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sysc.bulag_faust.post.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, UUID> {

  @Query("""

          SELECT DISTINCT p FROM Post p
          JOIN FETCH p.author
          LEFT JOIN FETCH p.categories
          LEFT JOIN FETCH p.tags

          WHERE (:categoryId IS NULL OR EXISTS(
                SELECT 1 FROM p.categories c WHERE c.id = :categoryId))
          AND (:tagId IS NULL OR EXISTS(
                SELECT 1 FROM p.tags t WHERE t.id = :tagId))

      """)

  List<Post> findAllByCategoryIdAndTagId(@Param("categoryId") UUID categoryId, @Param("tagId") UUID tagId);

}
