package com.musinsa.category.productcategory.query.application;

import com.musinsa.category.productcategory.query.dao.ProductCategoryDao;
import com.musinsa.category.productcategory.query.dto.ProductCategoryDto;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class ProductCategoryQueryService {

    private final ProductCategoryDao productCategoryDao;

    /**
     * 코드에 해당하는 상품카테고리 조회 서비스
     *
     * @param code 조회할 상품카테고리
     * @return 해당상품카테고리
     */
    public ProductCategoryDto getProductCategory(@NonNull String code) {
        return productCategoryDao.getProductCategory(code);
    }

    /**
     * 전체 상품카테고리 조회 서비스
     *
     * @return 전체 상품카테고리
     */
    public List<ProductCategoryDto> getAllProductCategories() {
        return productCategoryDao.getAllProductCategories();
    }
}
