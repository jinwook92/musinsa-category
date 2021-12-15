package com.musinsa.category.productcategory.query.dao;

import com.musinsa.category.productcategory.query.dto.ProductCategoryDto;

import java.util.List;

public interface ProductCategoryDao {
    List<ProductCategoryDto> getAllProductCategories();

    ProductCategoryDto getProductCategory(String productCategoryCode);

}
