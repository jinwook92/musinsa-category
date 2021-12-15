package com.musinsa.category.productcategory.command.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    @EntityGraph(attributePaths = {"children"})
    Optional<ProductCategory> findById(String code);
}
