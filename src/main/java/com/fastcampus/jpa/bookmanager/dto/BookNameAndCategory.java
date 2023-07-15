package com.fastcampus.jpa.bookmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookNameAndCategory {

    private String name;
    private String category;

}
