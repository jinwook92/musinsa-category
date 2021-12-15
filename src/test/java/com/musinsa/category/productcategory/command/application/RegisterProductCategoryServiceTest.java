package com.musinsa.category.productcategory.command.application;

import com.musinsa.category.productcategory.command.domain.DataNotFoundException;
import com.musinsa.category.productcategory.command.domain.ProductCategory;
import com.musinsa.category.productcategory.command.domain.ProductCategoryRepository;
import com.musinsa.category.productcategory.command.dto.ProductCategoryRegisterDto;
import com.musinsa.category.productcategory.query.dao.ProductCategoryDao;
import com.mysema.commons.lang.Assert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterProductCategoryServiceTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private RegisterProductCategoryService registerProductCategoryService;

    @AfterEach
    void tearDown() {
        productCategoryRepository.deleteAll();
    }

    @Test
    void registerProductCategory() {
        String code = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("TEST", 1, true));
        String childCode = registerProductCategoryService.registerChildProductCategory(code, new ProductCategoryRegisterDto("TEST_CHILD", 1, true));

        ProductCategory category = productCategoryRepository.findById(code).orElseThrow();
        Assertions.assertThat(category.getChildren().size()).isEqualTo(1);
    }

    @Test
    void registerChildCategoryWithoutParent() {
        assertThrows(DataNotFoundException.class, () ->
                registerProductCategoryService.registerChildProductCategory("TEST_CODE", new ProductCategoryRegisterDto("TEST_CHILD", 1, true)));
    }

}