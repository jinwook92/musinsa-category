package com.musinsa.category.productcategory.command.application;

import com.musinsa.category.productcategory.command.domain.DataNotFoundException;
import com.musinsa.category.productcategory.command.domain.ProductCategory;
import com.musinsa.category.productcategory.command.domain.ProductCategoryCodeGenerator;
import com.musinsa.category.productcategory.command.domain.ProductCategoryRepository;
import com.musinsa.category.productcategory.command.dto.ProductCategoryRegisterDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductCategoryCodeGenerator productCategoryCodeGenerator;

    /**
     * 최상위 상품카테고리 등록 서비스
     *
     * @param registerDto 등록 정보
     * @return 등록된 상품카테고리 코드
     */
    public String registerProductCategory(ProductCategoryRegisterDto registerDto) {
        ProductCategory category = ProductCategory.builder()
                .productCategoryCode(productCategoryCodeGenerator.generateNextCode(null))
                .productCategoryName(registerDto.getProductCategoryName())
                .sortOrder(registerDto.getSortOrder())
                .display(registerDto.isDisplay())
                .build();
        ProductCategory save = productCategoryRepository.save(category);
        return save.getProductCategoryCode();
    }

    /**
     * 하위 상품카테고리 등록 서비스
     *
     * @param parentCode  상위 상품카테고리 코드
     * @param registerDto 등록 정보
     * @return 등록된 상품카테고리 코드
     */
    public String registerChildProductCategory(String parentCode, ProductCategoryRegisterDto registerDto) {
        ProductCategory parent = productCategoryRepository.findById(parentCode)
                .orElseThrow(DataNotFoundException::new);
        ProductCategory category = ProductCategory.builder()
                .parent(parent)
                .productCategoryCode(productCategoryCodeGenerator.generateNextCode(parent.getProductCategoryCode()))
                .productCategoryName(registerDto.getProductCategoryName())
                .sortOrder(registerDto.getSortOrder())
                .display(registerDto.isDisplay())
                .build();
        ProductCategory save = productCategoryRepository.save(category);
        return save.getProductCategoryCode();
    }
}
