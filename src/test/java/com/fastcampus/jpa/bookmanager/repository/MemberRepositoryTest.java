package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Member;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jinan
 */
@SpringBootTest //springContext를 로딩해서 test를 하겠다는 의미
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
//    @Transactional //session이 유지됨
    void crud() {
//        Member member = memberRepository.getReferenceById(1L); // getReferenceById는 lazy fetch를 지원한다. proxy로 가지고 있다가 조회한다.
//        Member member = memberRepository.findById(1L).orElse(null);// findById는 Optional로 wrapping되어 있어 따로 처리해야한다. orElse로 처리해주면 entity만 꺼낼수 있다.
//        boolean exists = memberRepository.existsById(1L);
//        memberRepository.delete(memberRepository.findById(1L).orElseThrow(RuntimeException::new));
//        memberRepository.deleteById(1L);
        memberRepository.deleteAll(memberRepository.findAllById(Lists.newArrayList(1L, 3L)));
        memberRepository.findAll().forEach(System.out::println);

        System.out.println("============================result============================");
        System.out.println();
        System.out.println("============================result============================");

    }


}