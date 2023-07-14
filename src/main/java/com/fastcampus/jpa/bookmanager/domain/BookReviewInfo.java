package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@NoArgsConstructor
@ToString(callSuper = true) // 이걸 해줘야 BaseEntity의 생성일자, 업데이트일자를 볼수있다.
@Entity
public class BookReviewInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Book이 있어야만 review가 존재하니 optional=false가 맞다.
    @OneToOne(optional = false) //optional을 false로 주면 반드시 존재하는값이 된다. null을 허용하지 않는다.
    private Book book; //entity를 바로 참조하도록 한다.

    private float averageReviewScore;

    private int reviewCount;



}
