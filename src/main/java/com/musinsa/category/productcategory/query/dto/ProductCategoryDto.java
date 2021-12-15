package com.musinsa.category.productcategory.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDto {
    /**
     * 상품카테고리코드
     */
    private String productCategoryCode;
    /**
     * 상품카테고리명
     */
    private String productCategoryName;
    /**
     * 정렬순위
     */
    private Integer sortOrder;
    /**
     * 전시여부
     */
    private boolean display;
    /**
     * 하위상품카테고리
     */
    private List<ProductCategoryDto> children = new ArrayList<>();

    public ProductCategoryDto(String productCategoryCode,
                              String productCategoryName,
                              Integer sortOrder,
                              boolean display) {
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.sortOrder = sortOrder;
        this.display = display;
    }

    public void addChild(ProductCategoryDto child) {
        this.children.add(child);
    }

    public Optional<ProductCategoryDto> lookup(String productCategoryCode) {
        if (this.productCategoryCode.equals(productCategoryCode)) {
            return Optional.of(this);
        }

        for (ProductCategoryDto child : children) {
            var dto = child.lookup(productCategoryCode);
            if (dto.isPresent()) {
                return dto;
            } else {
                continue;
            }
        }
        return Optional.empty();
    }
}
