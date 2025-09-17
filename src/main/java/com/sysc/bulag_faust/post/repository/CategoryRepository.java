package com.sysc.bulag_faust.post.repository;

import com.sysc.bulag_faust.post.domain.entities.Category;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category,  UUID> {

    boolean existsByNameIgnoreCase(String name);

    //only good when posts per category is < 1K
    // filter in memory
    @EntityGraph(value = "graph.CategoryPost")
    List<Category> findByPostsIsNotEmpty();

    //alternative for large posts per category
    // fitler in database
    // @Query(
    //     "SELECT c, COUNT(p) FROM Category c LEFT JOIN c.posts p WHERE p.status = 'PUBLISHED' GROUP BY c HAVING COUNT(p) > 0"
    // )
    // List<Object[]> findCategoriesWithPublishedPostCount();
}
