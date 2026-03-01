package com.sysc.bulag_faust.category;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sysc.bulag_faust.category.dto.CategoryResponse;
import com.sysc.bulag_faust.post.entity.PostStatus;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

  // @EntityGraph(attributePaths = { "posts" }) // bad loads the posts but we only
  // need to count
  // List<Category> findAll();

  @Query("""
      SELECT new com.sysc.bulag_faust.category.dto.CategoryResponse
      (
      c.id,
      c.name,
      COUNT(p)
      )
      FROM Category c
      INNER JOIN c.posts p ON p.status = :status
      GROUP BY c.id, c.name
      """)
  List<CategoryResponse> findAllWithPublishedPostCounts(@Param("status") PostStatus status);

  boolean existsByNameIgnoreCase(String name);

  Category findByName(String originalName);

  boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

  boolean existsByIdAndPostsIsNotEmpty(UUID id);
}
