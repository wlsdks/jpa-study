package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Member;
import com.fastcampus.jpa.bookmanager.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@SpringBootTest
public class EntityManagerTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void entityManagerTest() {
        System.out.println(entityManager.createQuery("select m from Member m").getResultList());
    }

    @Test
    void cacheFindTest() {
        // 트랜잭션으로 묶으면 캐시설정을 안해도 자동으로 영속성 컨텍스트 안에서 1차캐싱으로 한번 쿼리한 데이터를 가지고있는다.(트랜잭션 안에서만)
        // 참고로 (1차 캐싱은) key로 조회할때만 적용된다. -> id로 찾을때만 [findById: O, findByEmail: X]
//        System.out.println(memberRepository.findById(1L).get());
//        System.out.println(memberRepository.findById(1L).get());
//        System.out.println(memberRepository.findById(1L).get());

        memberRepository.deleteById(1L);
    }

    @Test
    void cacheFindTest2() {
        Member member = memberRepository.findById(1L).get();

        member.setName("marrrrrrin");
        memberRepository.save(member);

        System.out.println("----------------------");

        member.setEmail("marrrrrrin@fastcampus.com");
        memberRepository.save(member);

        // flush로 쌓여있는걸 db에 영속화 시켜준다. 화장실 변기물 내리는것도 flush라 써있음
        // memberRepository.flush();
        System.out.println(memberRepository.findAll()); // select * from member
    }


}
