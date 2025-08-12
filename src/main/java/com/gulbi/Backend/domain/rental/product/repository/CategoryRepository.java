package com.gulbi.Backend.domain.rental.product.repository;

import com.gulbi.Backend.domain.rental.product.dto.category.CategoryProjection;
import com.gulbi.Backend.domain.rental.product.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
//    @Query("SELECT c1 FROM Category c1 LEFT JOIN c1.parent c2")
    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<CategoryProjection> findAllNoParentProjection();

    @Query("SELECT c FROM Category c WHERE c.parent IS NULL")
    List<Category> findAllNoParent();
    // 대분류만 꺼냄, 대분류는 부모님이 안계심

    @Query("SELECT c.id AS id, c.name AS name FROM Category c WHERE c.parent.id = :parentId")
    List<CategoryProjection> findBelowCategory(@Param("parentId") Long parentId);

    Optional<Category> findByName(String name);
    List<Category> findByParent(Category category);

}
