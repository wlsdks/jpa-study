package com.fastcampus.jpa.bookmanager.dto;

import lombok.Data;

@Data
public class BookStatus {

    private int code;
    private String description;

    public BookStatus(int code) {
        this.code = code;
        this.description = parseDescription(code);
    }

    private String parseDescription(int code) {
        // 자바8이상의 예쁜 람다형식의 switch문
         return switch (code) {
            case 100 -> "판매종료";
            case 200 -> "판매중";
            case 300 -> "판매보류";
            default -> "미지원";
        };
    }

    public boolean isDisplayed() {
        return code == 200;
    }

}
