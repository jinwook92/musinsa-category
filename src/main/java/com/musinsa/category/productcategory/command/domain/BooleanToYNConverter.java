package com.musinsa.category.productcategory.command.domain;

import javax.persistence.AttributeConverter;

public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    /**
     * Boolean 값을 "Y" 또는 "N"으로 변경
     * @param attribute boolean data
     * @return ture인 경우 "Y", false인 경우 "N"
     */
    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute != null && attribute ? "Y" : "N";
    }

    /**
     *  "Y", "N" 값을 boolean으로 변경
     * @param dbData dbData
     * @return "Y"인 경우 true, "N"인 경우 false
     */
    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return "Y".equals(dbData);
    }
}
