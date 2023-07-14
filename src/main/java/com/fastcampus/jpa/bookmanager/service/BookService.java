package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Author;
import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.repository.AuthorRepository;
import com.fastcampus.jpa.bookmanager.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

// final인 필드를 필요로하는 생성자를 만들어 준다.
@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final EntityManager entityManager;

    // 메서드의 시작이 트랜잭션의 시작이고 종료가 트랜잭션의 종료이다.
    // 트랜잭션을 안달면 save에서 트랜잭션을 동작하여 save를 사용하는것 한줄씩 트랜잭션으로 동작하게 된다.
//    @Transactional(rollbackFor = Exception.class)
    @Transactional
    public void putBookAndAuthor() {
        Book book = new Book();
        book.setName("JPA 시작하기");

        bookRepository.save(book);

        Author author = new Author();
        author.setName("martin");

        authorRepository.save(author);

//        throw new RuntimeException("오류가 나서 DB commit이 발생하지 않습니다.");
        throw new RuntimeException("오류가 나서 DB commit이 발생하지 않습니다.");
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void get(Long id) {
        System.out.println(">>> " + bookRepository.findById(id));
        System.out.println(">>> " + bookRepository.findAll());

        entityManager.clear();

        System.out.println(">>> " + bookRepository.findById(id));
        System.out.println(">>> " + bookRepository.findAll());

        bookRepository.update();

        entityManager.clear();

//        Book book = bookRepository.findById(id).get();
//        book.setName("바뀔까?");
//        bookRepository.save(book);
    }

    //    // 자기 자신의 트랜잭션 메소드를 호출
//    public void put() {
//        this.putBookAndAuthor();
//    }


    // 메서드의 시작이 트랜잭션의 시작이고 종료가 트랜잭션의 종료이다.
    // 트랜잭션을 안달면 save에서 트랜잭션을 동작하여 save를 사용하는것 한줄씩 트랜잭션으로 동작하게 된다.
//    @Transactional(rollbackFor = Exception.class)
//    @Transactional // 만약 지금처럼 @Bean 클래스 내부에서 내부의 메소드()를 호출하면 그때는 메소드의 어노테이션은 무효화된다. 주의하자! 트랜잭션이 안먹힌다.
//    public void putBookAndAuthor() {
//        Book book = new Book();
//        book.setName("JPA 시작하기");
//
//        bookRepository.save(book);
//
//        Author author = new Author();
//        author.setName("martin");
//
//        authorRepository.save(author);
//
////        throw new RuntimeException("오류가 나서 DB commit이 발생하지 않습니다.");
//        throw new RuntimeException("오류가 나서 DB commit이 발생하지 않습니다.");
//    }
//

}
