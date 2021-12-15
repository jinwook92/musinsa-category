package com.musinsa.category.productcategory.command.domain;

import com.musinsa.category.productcategory.command.dto.ProductCategoryModifyDto;
import com.mysema.commons.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_category")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {

    @Id
    @Column(name = "product_category_code", nullable = false, length = 15)
    private String productCategoryCode;

    @Column(name = "product_category_name", nullable = false, length = 50)
    private String productCategoryName;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_product_category_code", referencedColumnName = "product_category_code", nullable = true)
    private ProductCategory parent;

    @Column(name = "display_yn", length = 1)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean display;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductCategory> children = new ArrayList<>();


    @Builder
    public ProductCategory(String productCategoryCode,
                           String productCategoryName,
                           Integer sortOrder,
                           boolean display,
                           ProductCategory parent) {
        this.productCategoryCode = productCategoryCode;
        this.productCategoryName = productCategoryName;
        this.sortOrder = sortOrder;
        this.display = display;
        this.parent = parent;
    }

    public void modify(ProductCategoryModifyDto modifyDto) {
        this.productCategoryName = modifyDto.getProductCategoryName();
        this.display = modifyDto.isDisplay();
        this.sortOrder = modifyDto.getSortOrder();
    }
}
