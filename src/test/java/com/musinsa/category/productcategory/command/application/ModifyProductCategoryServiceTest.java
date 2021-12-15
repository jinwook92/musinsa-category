package com.musinsa.category.productcategory.command.application;

import com.musinsa.category.productcategory.command.domain.ProductCategory;
import com.musinsa.category.productcategory.command.domain.ProductCategoryRepository;
import com.musinsa.category.productcategory.command.dto.ProductCategoryModifyDto;
import com.musinsa.category.productcategory.command.dto.ProductCategoryRegisterDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ModifyProductCategoryServiceTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private RegisterProductCategoryService registerProductCategoryService;

    @Autowired
    private ModifyProductCategoryService modifyProductCategoryService;

    @AfterEach
    void tearDown() {
        productCategoryRepository.deleteAll();
    }

    @Test
    void modifyProductCategory() {
        String code = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("TEST", 1, true));
        modifyProductCategoryService.modifyProductCategory(code, new ProductCategoryModifyDto("TEST_NAME", 3, false));

        ProductCategory category = productCategoryRepository.findById(code).orElseThrow();

        Assertions.assertThat(category.getProductCategoryName()).isEqualTo("TEST_NAME");
        Assertions.assertThat(category.isDisplay()).isEqualTo(false);
        Assertions.assertThat(category.getSortOrder()).isEqualTo(3);
    }
}