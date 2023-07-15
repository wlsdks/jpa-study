package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Address;
import com.fastcampus.jpa.bookmanager.domain.Gender;
import com.fastcampus.jpa.bookmanager.domain.Member;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

/**
 * @author jinan
 */
@Transactional
@SpringBootTest //springContext를 로딩해서 test를 하겠다는 의미
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Autowired
    private EntityManager entityManager;

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

    @Test
    void pagingAndSortingTest() {
//        System.out.println("findTop1ByName : " + memberRepository.findTop1ByName("wlsdks"));
//        System.out.println("findLast1ByName : " + memberRepository.findLast1ByName("wlsdks"));
//        System.out.println("findTopByNameOrderByIdDesc : " + memberRepository.findTopByNameOrderByIdDesc("wlsdks"));
//        System.out.println("findFirstByNameOrderByIdDescEmailAsc : " + memberRepository.findFirstByNameOrderByIdDescEmailAsc("wlsdks"));
        System.out.println("findFirstByNameWithSortParams : " + memberRepository.findFirstByName("wlsdks", Sort.by(Sort.Order.desc("id"), Sort.Order.asc("email"))));

    }

    @Test
    void insertAndUpdateTest() {
        Member member = new Member();
        member.setName("wlsdks");
        member.setEmail("wlsdks132@naver.com");

        memberRepository.save(member);

        Member member1 = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        member1.setName("wlsssssssdks");

        memberRepository.save(member1);
    }

    @Test
    void enumTest() {
        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        member.setGender(Gender.MALE);

        memberRepository.save(member);

        memberRepository.findAll().forEach(System.out::println);
        System.out.println(memberRepository.findRawRecord().get("gender"));
    }

    @Test
    void listenerTest() {
        Member member = new Member();
        member.setName("martin");
        member.setEmail("martin2@fastcampus.com");

        memberRepository.save(member);

        Member member1 = memberRepository.findById(1L).orElseThrow(RuntimeException::new);
        member1.setName("marttttttin");

        memberRepository.save(member1);

        memberRepository.deleteById(4L);
    }

    @Test
    void persistTest() {
        Member member = new Member();
        member.setEmail("martin2@fastcampus.com");
        member.setName("martin");
//        member.setCreatedAt(LocalDateTime.now());
//        member.setUpdatedAt(LocalDateTime.now());

        memberRepository.save(member); //insert문 날림

        System.out.println(memberRepository.findByEmail("martin2@fastcampus.com"));
    }

    @Test
    void preUpdateTest() {
        Member member = memberRepository.findById(1L).orElseThrow(RuntimeException::new);

        System.out.println("as-is : " + member);

        member.setName("martin22");
        memberRepository.save(member);

        System.out.println("to-be : " + memberRepository.findAll().get(0));
    }

    @Test
    void userHistoryTest() {
        Member member = new Member();
        member.setEmail("martin-new@naver.com");
        member.setName("martin-new");

        memberRepository.save(member);
        member.setName("martin-new-new");

        memberRepository.save(member);
        userHistoryRepository.findAll().forEach(System.out::println);
    }

    @Test
    void userRelationTest() {

        Member member = new Member();
        member.setName("david");
        member.setEmail("david@fastcampus.com");
        member.setGender(Gender.MALE);

        memberRepository.save(member);

        // jpa에서는 set으로 값을 변경하고 save를 하면 자동으로 update문을 쳐준다.
        member.setName("daniel");
        memberRepository.save(member);

        // jpa에서는 set으로 값을 변경하고 save를 하면 자동으로 update문을 쳐준다.
        member.setEmail("wlsdks@fastcampus.com");
        memberRepository.save(member);

        userHistoryRepository.findAll().forEach(System.out::println);

        List<UserHistory> result = memberRepository.findByEmail("wlsdks12@naver.com").getMemberHistories();

        result.forEach(System.out::println);

        System.out.println("UserHistory.getMember(): " + userHistoryRepository.findAll().get(0).getMember());

    }

    @DisplayName("embeddable 테스트")
    @Test
    void embedTest() {
        memberRepository.findAll().forEach(System.out::println);

        Member member = new Member();
        member.setName("steve");
        member.setHomeAddress(new Address("서울시", "강남구", "강남대로 364 미왕빌딩", "06241"));
        member.setCompanyAddress(new Address("서울시", "성동구", "성수이로 113 제강빌딩", "04794"));

        memberRepository.save(member);

        Member member1 = new Member();
        member1.setName("joshua");
        member1.setHomeAddress(null);
        member1.setCompanyAddress(null);

        memberRepository.save(member1);

        Member member2 = new Member();
        member2.setName("jordan");
        member2.setHomeAddress(new Address());
        member2.setCompanyAddress(new Address());

        memberRepository.save(member2);

        // 캐시를 한번 날려준다.
        entityManager.clear();

        memberRepository.findAll().forEach(System.out::println);
        userHistoryRepository.findAll().forEach(System.out::println);

        memberRepository.findAllRawRecord().forEach(a -> System.out.println(a.values()));

    }

}