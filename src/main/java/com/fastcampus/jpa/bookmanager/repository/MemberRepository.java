package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jinan
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    Set<Member> findByName(String name);

    Set<Member> findMemberByNameIs(String name);

    Set<Member> findMemberByName(String name);

    Set<Member> findMemberByNameEquals(String name);

    Member findByEmail(String email);

    Member getByEmail(String email);

    Member readByEmail(String email);

    Member queryByEmail(String email);

    Member searchByEmail(String email);

    Member streamByEmail(String email);

    Member findMemberByEmail(String email);

    Member findSomethingByEmail(String email);

//    List<Member> findFirst1ByName(String name);
    Member findFirst1ByName(String name);

//    List<Member> findTop1ByName(String name);
    Member findTop1ByName(String name);

    List<Member> findLast1ByName(String name);

    List<Member> findByEmailAndName(String email, String name);

    List<Member> findByEmailOrName(String email, String name);

    List<Member> findByCreatedAtAfter(LocalDateTime yesterday);

    List<Member> findByIdAfter(Long id);

    List<Member> findByCreatedAtGreaterThan(LocalDateTime yesterday);

    List<Member> findByCreatedAtGreaterThanEqual(LocalDateTime yesterday);

    List<Member> findByCreatedAtBetween(LocalDateTime yesterday, LocalDateTime tomorrow);

    List<Member> findByIdBetween(Long id, Long id2);

    List<Member> findByIdGreaterThanEqualAndIdLessThanEqual(Long id, Long id2);

    List<Member> findByIdIsNotNull();

//    List<Member> findByAddressesIsNotEmpty(); // address is not null and name != '' ??

    List<Member> findByNameIn(List<String> names);

    List<Member> findByNameStartingWith(String name);

    List<Member> findByNameEndingWith(String name);

    List<Member> findByNameContains(String name);

    List<Member> findByNameLike(String name);

    List<Member> findTopByNameOrderByIdDesc(String name); // orderBy로 id를 (desc) 역순으로 정렬한다.

    List<Member> findFirstByNameOrderByIdDescEmailAsc(String name);

    List<Member> findFirstByName(String name, Sort sort);

    Page<Member> findByName(String name, Pageable pageable);

    @Query(value = "select * from Member limit 1;", nativeQuery = true)
    Map<String, Object> findRawRecord();

}
