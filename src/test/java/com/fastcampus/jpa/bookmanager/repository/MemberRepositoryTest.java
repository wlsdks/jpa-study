package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Member;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    void select_테스트() {
//        System.out.println(memberRepository.findByName("dennis"));
//
//        System.out.println("findByEmail : " + memberRepository.findByEmail("wlsdks12@naver.com"));
//        System.out.println("getByEmail : " + memberRepository.getByEmail("wlsdks12@naver.com"));
//        System.out.println("readByEmail : " + memberRepository.readByEmail("wlsdks12@naver.com"));
//        System.out.println("queryByEmail : " + memberRepository.queryByEmail("wlsdks12@naver.com"));
//        System.out.println("searchByEmail : " + memberRepository.searchByEmail("wlsdks12@naver.com"));
//        System.out.println("streamByEmail : " + memberRepository.streamByEmail("wlsdks12@naver.com"));
//        System.out.println("findMemberByEmail : " + memberRepository.findMemberByEmail("wlsdks12@naver.com"));
//        System.out.println("findSomethingByEmail : " + memberRepository.findSomethingByEmail("wlsdks12@naver.com"));
//
//        // where 절에 limit가 들어간다.
//        System.out.println("findTop1ByName : " + memberRepository.findTop1ByName("wlsdks"));
//        System.out.println("findFirst1ByName : " + memberRepository.findFirst1ByName("wlsdks"));
//        System.out.println("findLast1ByName : " + memberRepository.findLast1ByName("wlsdks"));

//        System.out.println("findByEmailAndName : " + memberRepository.findByEmailAndName("wlsdks12@naver.com", "dennis"));
//        System.out.println("findByEmailOrName : " + memberRepository.findByEmailOrName("wlsdks12@naver.com", "dennis"));
//        System.out.println("findByCreatedAtAfter : " + memberRepository.findByCreatedAtAfter(LocalDateTime.now().minusDays(1L)));
//        System.out.println("findByIdAfter : " + memberRepository.findByIdAfter(4L));
//        System.out.println("findByCreatedAtGreaterThan : " + memberRepository.findByCreatedAtGreaterThan(LocalDateTime.now().minusDays(1L)));
//        System.out.println("findByCreatedAtGreaterThanEqual : " + memberRepository.findByCreatedAtGreaterThanEqual(LocalDateTime.now().minusDays(1L)));
//        System.out.println("findByCreatedAtBetween : " + memberRepository.findByCreatedAtBetween(LocalDateTime.now().minusDays(1L), LocalDateTime.now().plusDays(1L)));
//        System.out.println("findByIdBetween : " + memberRepository.findByIdBetween(1L, 3L));
//        System.out.println("findByIdGreaterThanEqualAndIdLessThanEqual : " + memberRepository.findByIdGreaterThanEqualAndIdLessThanEqual(1L, 3L));
//        System.out.println("findByIdIsNotNull : " + memberRepository.findByIdIsNotNull());
//        System.out.println("findByIdIsNotEmpty : " + memberRepository.findByIdIsNotEmpty());

    }

    @Test
    void newTest() {
//        System.out.println("findByAddressesIsNotEmpty : " + memberRepository.findByAddressesIsNotEmpty());
//        System.out.println("findByNameIn : " + memberRepository.findByNameIn(Lists.newArrayList("wlsdks", "jinan")));
//        System.out.println("findByNameStartingWith : " + memberRepository.findByNameStartingWith("wls"));
//        System.out.println("findByAddressesIsNotEmpty : " + memberRepository.findByNameEndingWith("dks"));
//        System.out.println("findByNameContains : " + memberRepository.findByNameContains("lsd"));
        System.out.println("findByNameLike : " + memberRepository.findByNameLike("%ls%"));

    }

}