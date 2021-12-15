package com.musinsa.category.productcategory.infra.dao;

import com.musinsa.category.productcategory.command.domain.ProductCategory;
import com.musinsa.category.productcategory.command.domain.QProductCategory;
import com.musinsa.category.productcategory.query.dao.ProductCategoryDao;
import com.musinsa.category.productcategory.query.dto.ProductCategoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProductCategoryDaoImpl implements ProductCategoryDao {

    private final JPAQueryFactory factory;

    @Override
    public ProductCategoryDto getProductCategory(String productCategoryCode) {
        QProductCategory qProductCategory = QProductCategory.productCategory;
        ProductCategory category = factory.select(qProductCategory)
                .from(qProductCategory)
                .where(qProductCategory.productCategoryCode.eq(productCategoryCode))
                .fetchOne();

        return mapToTree(category);
    }

    @Override
    public List<ProductCategoryDto> getAllProductCategories() {
        QProductCategory qProductCategory = QProductCategory.productCategory;

        List<ProductCategory> productCategoryList = factory.select(qProductCategory)
                .from(qProductCategory)
                .orderBy(qProductCategory.productCategoryCode.asc(), qProductCategory.sortOrder.asc())
                .fetch();
        return mapToTreeList(productCategoryList);
    }

    private ProductCategoryDto mapToTree(ProductCategory category) {
        if (category != null) {
            ProductCategoryDto categoryDto = getProductCategoryDto(category);
            for (ProductCategory child : category.getChildren()) {
                categoryDto.addChild(mapToTree(child));
            }
            return categoryDto;
        } else {
            return null;
        }
    }

    private List<ProductCategoryDto> mapToTreeList(List<ProductCategory> categories) {
        List<ProductCategoryDto> tree = new ArrayList<>();
        for (ProductCategory category : categories) {
            if (category.getParent() == null) {
                ProductCategoryDto root = getProductCategoryDto(category);
                tree.add(root);
            } else {
                for (ProductCategoryDto dto : tree) {
                    Optional<ProductCategoryDto> treeNode = dto.lookup(category.getParent().getProductCategoryCode());
                    if (treeNode.isPresent()) {
                        ProductCategoryDto childNode = getProductCategoryDto(category);
                        treeNode.get().addChild(childNode);
                        break;
                    }
                }
            }
        }
        return tree;
    }

    private ProductCategoryDto getProductCategoryDto(ProductCategory category) {
        return new ProductCategoryDto(category.getProductCategoryCode(),
                category.getProductCategoryName(), category.getSortOrder(), category.isDisplay());
    }
}
