package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.repository.AuthorRepository;
import com.fastcampus.jpa.bookmanager.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void transactionTest() {
        // try-catch로 이렇게 하는것은 안티패턴적인 작성법이다. 테스트에서 임의로 원래생기는 에러를 막은것이기 때문이다.
        // 최근 트랜드 : 트랜잭션 내에서는 unchecked Exception(RuntimeException)을 사용함 !!checked Exception(Exception)을 쓰면 rollback이 되지 않음!! 그래서 처리 핸들링을 해줘야함
        try {
            bookService.putBookAndAuthor();
        } catch (RuntimeException e) {
            System.out.println(">>> " + e.getMessage());
        }

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("authors : " + authorRepository.findAll());
    }

    @Test
    void isolationTest() {
        Book book = new Book();
        book.setName("JPA 강의");

        bookRepository.save(book);

        bookService.get(1L);

        System.out.println(">>> " + bookRepository.findAll());
    }

    @DisplayName("Converter를 양방향 모두 구현하지 않으면 데이터가 유실된다.")
    @Test
    void converterErrorTest() {
        bookService.getAll();

        bookRepository.findAll().forEach(System.out::println);
    }

}