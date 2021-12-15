package com.musinsa.category.productcategory.query.application;

import com.musinsa.category.productcategory.command.application.RegisterProductCategoryService;
import com.musinsa.category.productcategory.command.domain.ProductCategoryRepository;
import com.musinsa.category.productcategory.command.dto.ProductCategoryRegisterDto;
import com.musinsa.category.productcategory.query.dto.ProductCategoryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductCategoryQueryServiceTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryQueryService productCategoryQueryService;

    @Autowired
    private RegisterProductCategoryService registerProductCategoryService;

    @AfterEach
    void tearDown() {
        productCategoryRepository.deleteAll();
    }

    @Test
    void getProductCategory() {
        String code = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("TEST", 1, true));
        String childCode1 = registerProductCategoryService.registerChildProductCategory(code, new ProductCategoryRegisterDto("TEST_CHILD1", 1, true));
        String childCode2 = registerProductCategoryService.registerChildProductCategory(code, new ProductCategoryRegisterDto("TEST_CHILD2", 2, true));

        ProductCategoryDto productCategory = productCategoryQueryService.getProductCategory(code);

        Assertions.assertThat(productCategory.getChildren().size()).isEqualTo(2);
    }

    @Test
    void getAllProductCategories() {
        String code1 = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("TEST", 1, true));
        String childCode1 = registerProductCategoryService.registerChildProductCategory(code1, new ProductCategoryRegisterDto("TEST_CHILD1", 1, true));
        String childCode2 = registerProductCategoryService.registerChildProductCategory(code1, new ProductCategoryRegisterDto("TEST_CHILD2", 2, true));

        String code2 = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("TEST", 1, true));
        String childCode3 = registerProductCategoryService.registerChildProductCategory(code2, new ProductCategoryRegisterDto("TEST_CHILD1", 1, true));
        String childCode4 = registerProductCategoryService.registerChildProductCategory(code2, new ProductCategoryRegisterDto("TEST_CHILD2", 2, true));

        List<ProductCategoryDto> list = productCategoryQueryService.getAllProductCategories();

        Assertions.assertThat(list.size()).isEqualTo(2);
        Assertions.assertThat(list.get(0).getChildren().size()).isEqualTo(2);
    }
}