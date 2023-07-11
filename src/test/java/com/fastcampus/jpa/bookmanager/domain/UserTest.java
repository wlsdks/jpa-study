package com.fastcampus.jpa.bookmanager.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void test() {
        User user = new User();
        user.setEmail("wlsdks12@naver.com");
        user.setName("jinan");

        System.out.println(">>>" + user);

        User user3 = User.builder()
                .name("jinan")
                .email("wlsdks12@naver.com")
                .build();
    }

}