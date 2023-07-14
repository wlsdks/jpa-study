package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 팬텀 read를 제연하기위함
    @Modifying
    @Query(value = "update Book set category = 'none'", nativeQuery = true)
    void update();

    List<Book> findByCategoryIsNull();

    // deleted를 넣어주면 실수하면 힘들다..
    List<Book> findAllByDeletedFalse();

    List<Book> findByCategoryIsNullAndDeletedFalse();


}
