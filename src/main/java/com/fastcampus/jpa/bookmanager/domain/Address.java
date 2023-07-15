package com.fastcampus.jpa.bookmanager.domain;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable // 임베드가 가능하다고 선언해준다.
public class Address {

    private String city;      // 시

    private String district;  // 구

    @Column(name = "address_detail")
    private String detail;    // 상세주소

    private String zipCode;   // 우편번호

}
