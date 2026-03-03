package com.sysc.bulag_faust.category;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sysc.bulag_faust.category.dto.CategoryResponse;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

  @Query("""
      SELECT new com.sysc.bulag_faust.category.dto.CategoryResponse
      (
      c.id,
      c.name,
      COUNT(p)
      )
      FROM Category c
      LEFT JOIN c.posts p
      GROUP BY c.id, c.name
      """)
  Page<CategoryResponse> findAllWithPostCounts(Pageable pageable);

  boolean existsByNameIgnoreCase(String name);

  Category findByName(String originalName);

  boolean existsByNameIgnoreCaseAndIdNot(String name, UUID id);

  boolean existsByIdAndPostsIsNotEmpty(UUID id);
}
