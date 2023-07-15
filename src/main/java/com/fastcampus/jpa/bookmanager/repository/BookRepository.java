package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.dto.BookNameAndCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 팬텀 read를 제연하기위함
    @Modifying
    @Query(value = "update book set category = 'none'", nativeQuery = true)
    void update();

    List<Book> findByCategoryIsNull();

    // deleted를 넣어주면 실수하면 힘들다..
    List<Book> findAllByDeletedFalse();

    List<Book> findByCategoryIsNullAndDeletedFalse();

    // 이름이 너무 길다.
    List<Book> findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(String name, LocalDateTime createdAt, LocalDateTime updatedAt);


    // 위의 코드를 이렇게 @Query를 통해서 줄일수가 있다. -> JPQL사용 (dialect 방언을 통해 db종류에 따라 맞는 쿼리를 알아서 생성해준다.)
    // 아래의 쿼리처럼 ?1, ?2, ?3 이런식으로 파라미터를 세팅해주는것을 자바는 지양한다.
    @Query(value = "select b from Book b "
            + "where b.name = ?1 and b.createdAt >= ?2 and b.updatedAt >= ?3 and b.category is null")
    List<Book> findByNameRecently(String name, LocalDateTime createdAt, LocalDateTime updatedAt);

    // 이 방식을 권장한다. (:) 콜롬을 앞에 적고 뒤에 param의 이름을 적어주면 된다.
    // 실제의 db는 많은 컬럼을 가진다. 이때 필요한 컬럼만 꺼내서 사용하기 위해 이렇게 @Query를 통해서 사용한다.
    @Query(value = "select b from Book b "
            + "where b.name = :name and b.createdAt >= :createdAt and b.updatedAt >= :updatedAt and b.category is null")
    List<Book> findByNameRecently2(
            @Param("name") String name,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    // 필요한 값만 interface or dto로 가져올수도 있다.
    @Query(value = "select new com.fastcampus.jpa.bookmanager.dto.BookNameAndCategory(b.name as name, b.category as category) from Book b")
    List<BookNameAndCategory> findBookNameAndCategory();

    // 오버로딩 - 이름은 같지만 파라미터가 다르면 2개의 같은 이름으로 존재할수가 있다. sort
    @Query(value = "select new com.fastcampus.jpa.bookmanager.dto.BookNameAndCategory(b.name as name, b.category as category) from Book b")
    Page<BookNameAndCategory> findBookNameAndCategory(Pageable pageable);

    /**
     * Native Query 사용
     * Native Query의 문법에서는 entity속성을 사용할수 없다.(from절에는 table명이 들어감 entity가아님)
     * Native Query를 사용하는것은 다른 db로 바꾸게 될 경우 매우 힘들게 된다. 현업에서는 이것이 중요하게 작용됨(native query는 최소한으로 사용하는것이 권장됨)
     * 성능 최적화를 위해 사용된다.(배치 처리처럼 한번에 삭제같은 동작을 할수 있도록 하기 위함)
     */
    @Query(value = "select * from book", nativeQuery = true)
    List<Book> findAllCustom();

    /** 이렇게 native query로 배치처리처럼 한번의 쿼리로 업데이트 처리가 가능하다.
     * return으로 업데이트된 row의 수를 반환받는다. affected row 라고 한다.
     * update문은 @Modifying을 해줘야 한다.
     * native Query를 쓸때는 @Transactional을 직접 작성해줘야 한다.
     * native Query는 우리가 @Where에 달아준 deleted=false조건도 무시한다.
     */
    @Transactional
    @Modifying
    @Query(value = "update book set category = 'IT전문서'", nativeQuery = true)
    int updateCategories();

    // 이렇게 일반적인 jpa가 지원하지 않는 쿼리를 nativeQuery로 사용할수가 있다.
    @Query(value = "show tables", nativeQuery = true)
    List<String> showTables();

    // id 역순으로 1개만 가져온다.
    // enum 이 있으면 이렇게 조회한다.
    @Query(value = "select * from book ORDER BY id desc limit 1", nativeQuery = true)
    Map<String, Object> findRawRecord();


}
