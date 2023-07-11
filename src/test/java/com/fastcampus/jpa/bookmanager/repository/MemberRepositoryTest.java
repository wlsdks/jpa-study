package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

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
//        memberRepository.deleteAll(memberRepository.findAllById(Lists.newArrayList(1L, 3L)));
//        memberRepository.deleteAllInBatch(); // 이렇게하면 반복문없이 delete쿼리를 돌림(최적화)
//        memberRepository.deleteAllInBatch(memberRepository.findAllById(Lists.newArrayList(1L, 3L))); // 이렇게하면 반복문없이 delete쿼리를 돌림(최적화)

        memberRepository.findAll().forEach(System.out::println);

    }

    @Test
    void 페이징() {
        Page<Member> members = memberRepository.findAll(PageRequest.of(0, 3));

        System.out.println("page : " + members);
        System.out.println("totalElements : " + members.getTotalElements()); // 총 데이터 갯수
        System.out.println("totalPages : " + members.getTotalPages()); // 전체 페이지 갯수
        System.out.println("numberOfElements : " + members.getNumberOfElements()); // 페이지 안의 데이터 갯수를 가져옴
        System.out.println("sort : " + members.getSort());
        System.out.println("size : " + members.getSize()); // 내가 of에 size로 3을 넣어줌

        members.getContent().forEach(System.out::println);
    }

    @DisplayName("example보단 차라리 querydsl을 사용한다.")
    @Test
    void 페이징_exampleMatcher_likeMatch() {
        // ExampleMatcher로 like검색을 한다.
        ExampleMatcher matcher = ExampleMatcher.matching()
//                .withIgnorePaths("name") // 이건 확인안함(매칭에서 무시함)
//                .withMatcher("email", endsWith()); // 이걸로 걸어줘야만 like검색을함 위의 name은 exact = 매칭임
                .withMatcher("email", contains()); // contains는 양방향 매칭이다.

        Example<Member> example = Example.of(new Member("ma", "ma@naver.com"), matcher); //probe에는 가짜 entity를 넣어준다.

        memberRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void 페이징_exactMatch() {
        // ExampleMatcher로 like검색을 한다.
        Example<Member> example = Example.of(new Member("ma", "ma@naver.com")); //probe에는 가짜 entity를 넣어준다.

        memberRepository.findAll(example).forEach(System.out::println);
    }

    @Test
    void 업데이트() {
        memberRepository.save(new Member("david", "david@naver.com"));

        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        member.setEmail("wlsdks1234@naver.com");

        memberRepository.save(member);
    }

}