package jpabook.entity.converter;

import javax.persistence.AttributeConverter;

/**
 * https://github.com/holyeye/jpabook
 */
// @Converter(autoApply = true) // 글로벌 설정
public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean aBoolean) {
        return (aBoolean != null && aBoolean) ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String s) {
        return "Y".equals(s);
    }
}
