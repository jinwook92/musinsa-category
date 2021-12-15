package com.musinsa.category.productcategory.command.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductCategoryCodeGenerator {

    private final JPAQueryFactory factory;

    public ProductCategoryCodeGenerator(JPAQueryFactory factory) {
        this.factory = factory;
    }

    /**
     * 상품카테고리 코드를 생성한다.
     * <p>
     * 코드 규칙은 다음과 같다.
     * <p>
     * 최상위 카테고리의 경우 0001, 0002의 네자리 숫자 문자열로 구성한다.
     * <p>
     * 자식 카테고리의 경우 부모코드 + 0001의 규칙으로 구성된다.
     *
     * 예시) 00010001, 00010002
     *
     * @param parentCode 부모코드
     * @return 규칙에 해당하는 다음 상품카테고리 코드
     */
    public String generateNextCode(String parentCode) {
        QProductCategory qProductCategory = QProductCategory.productCategory;
        if (StringUtils.isEmpty(parentCode)) {
            String code = factory.select(qProductCategory.productCategoryCode.max())
                    .from(qProductCategory)
                    .where(qProductCategory.parent.productCategoryCode.isNull())
                    .fetchOne();

            return nextParentCode(code);
        } else {
            String code = factory.select(qProductCategory.productCategoryCode.max())
                    .from(qProductCategory)
                    .where(qProductCategory.parent.productCategoryCode.eq(parentCode))
                    .fetchOne();
            return nextCode(parentCode, code);
        }
    }

    private String nextParentCode(String code) {
        if (StringUtils.isEmpty(code)) {
            return "0001";
        } else {
            int max = Integer.parseInt(code);
            int next = max + 1;
            return StringUtils.leftPad(String.valueOf(next), 4, "0");
        }
    }

    private String nextCode(String parentCode, String maxCode) {
        if (StringUtils.isEmpty(maxCode)) {
            return parentCode + "0001";
        }
        String nextCode = nextParentCode(maxCode.substring(parentCode.length()));
        return parentCode + nextCode;
    }
}
