package com.fastcampus.jpa.bookmanager.domain;

import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void test() {
        Member member = new Member();
        member.setEmail("wlsdks12@naver.com");
        member.setName("jinan");

        System.out.println(">>>" + member);

        Member member3 = Member.builder()
                .name("jinan")
                .email("wlsdks12@naver.com")
                .build();
    }

}