package com.musinsa.category.productcategory.command.application;

import com.musinsa.category.productcategory.command.domain.DataNotFoundException;
import com.musinsa.category.productcategory.command.domain.ProductCategory;
import com.musinsa.category.productcategory.command.domain.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    /**
     * 상품카테고리 삭제 서비스
     *
     * @param productCategoryCode 삭제 대상 상품카테고리 코드
     */
    public void deleteProductCategory(String productCategoryCode) {
        ProductCategory category = productCategoryRepository.findById(productCategoryCode)
                .orElseThrow(DataNotFoundException::new);

        productCategoryRepository.delete(category);
    }
}
