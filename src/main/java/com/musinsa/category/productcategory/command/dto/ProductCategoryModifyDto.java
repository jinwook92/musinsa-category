package com.musinsa.category.productcategory.command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryModifyDto {
    /**
     * 상품카테고리명
     */
    @NotBlank
    private String productCategoryName;
    /**
     * 정렬순위
     */
    @NotNull
    private Integer sortOrder;
    /**
     * 전시여부
     */
    private boolean display;
}
