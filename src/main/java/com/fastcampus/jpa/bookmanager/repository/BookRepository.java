package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.dto.BookNameAndCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Tuple;
import java.time.LocalDateTime;
import java.util.List;

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



}
