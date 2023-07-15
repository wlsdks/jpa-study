package com.fastcampus.jpa.bookmanager.domain.converter;

import com.fastcampus.jpa.bookmanager.dto.BookStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * entity converter 구현
 * 최대한 오류가 없도록 null에 대한 별도의 처리가 필요하다.
 * 컨버터라는것은 양쪽의 변환로직이 모두 존재해야 한다. -> 데이터 유실이 생길수도 있다.
 * autoApply를 켜주는게 좋다.
 * (참고로 String,Integer,Long 의 converter를 만들었다면 그때는 autoApply를 끄고 각각 필드에 @Convert(converter)를 해줘야 문제가 없을것이다.
 * 왜냐면 모든 String, Integer, Long이 컨버터를 타게되어 원하지 않던 부분도 변환이 될것이기 때문이다.
 */
@Converter(autoApply = true)
public class BookStatusConverter implements AttributeConverter<BookStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(BookStatus attribute) {
        return attribute.getCode();
//        return null; // 사용안함 -> 이렇게 구현을 안해주면 영속성 컨텍스트가 이상하게 받아들이기 때문에 데이터가 유실된다.
    }

    @Override
    public BookStatus convertToEntityAttribute(Integer dbData) {
        // 3항 연산자는 클린코드에서는 지양하는 패턴이다.(복잡하다.)
        return dbData != null ? new BookStatus(dbData) : null;
    }

}
