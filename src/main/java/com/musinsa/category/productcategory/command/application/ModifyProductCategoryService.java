package com.musinsa.category.productcategory.command.application;

import com.musinsa.category.productcategory.command.domain.DataNotFoundException;
import com.musinsa.category.productcategory.command.domain.ProductCategory;
import com.musinsa.category.productcategory.command.domain.ProductCategoryRepository;
import com.musinsa.category.productcategory.command.dto.ProductCategoryModifyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
public class ModifyProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    /**
     * 상품카테고리 수정 서비스
     *
     * @param productCategoryCode 대상 상품카테고리 코드
     * @param modifyDto           수정 정보
     */
    public void modifyProductCategory(String productCategoryCode,
                                      ProductCategoryModifyDto modifyDto) {
        ProductCategory productCategory = productCategoryRepository.findById(productCategoryCode)
                .orElseThrow(DataNotFoundException::new);

        productCategory.modify(modifyDto);
    }
}
