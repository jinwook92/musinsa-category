package com.musinsa.category.productcategory.command.application;

import com.musinsa.category.productcategory.command.domain.DataNotFoundException;
import com.musinsa.category.productcategory.command.domain.ProductCategory;
import com.musinsa.category.productcategory.command.domain.ProductCategoryRepository;
import com.musinsa.category.productcategory.command.dto.ProductCategoryRegisterDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class DeleteProductCategoryServiceTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private RegisterProductCategoryService registerProductCategoryService;

    @Autowired
    private DeleteProductCategoryService deleteProductCategoryService;

    @AfterEach
    void tearDown() {
        productCategoryRepository.deleteAll();
    }

    @Test
    void deleteProductCategory() {
        String code = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("test", 1, true));
        deleteProductCategoryService.deleteProductCategory(code);

        assertThrows(DataNotFoundException.class, () -> productCategoryRepository.findById(code)
                .orElseThrow(DataNotFoundException::new));
    }

    @Test
    void deleteNotExistProductCategory() {
        assertThrows(DataNotFoundException.class,
                () -> deleteProductCategoryService.deleteProductCategory("TEST"));
    }

    @Test
    void deleteParentProductCategory() {
        String code = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("test", 1, true));
        String childCode = registerProductCategoryService.registerChildProductCategory(code, new ProductCategoryRegisterDto("test", 1, true));
        deleteProductCategoryService.deleteProductCategory(code);

        assertThrows(DataNotFoundException.class, () -> productCategoryRepository.findById(childCode)
                .orElseThrow(DataNotFoundException::new));
    }

    @Test
    void deleteChildCategory() {
        String code = registerProductCategoryService.registerProductCategory(new ProductCategoryRegisterDto("test", 1, true));
        String childCode = registerProductCategoryService.registerChildProductCategory(code, new ProductCategoryRegisterDto("test", 1, true));
        deleteProductCategoryService.deleteProductCategory(childCode);

        ProductCategory category = productCategoryRepository.findById(code).orElseThrow();
        Assertions.assertThat(category.getChildren().size()).isEqualTo(0);
    }
}