package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.Member;
import com.fastcampus.jpa.bookmanager.domain.Publisher;
import com.fastcampus.jpa.bookmanager.domain.Review;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void bookTest() {
        Book book = new Book();
        book.setName("Jpa 초격차 패키지");
        book.setAuthorId(1L);
//        book.setPublisherId(1L);

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());

    }

    @Test
    @Transactional
    void bookRelationTest() {
        givenBookAndReview();

        Member member = memberRepository.findByEmail("wlsdks12@naver.com");

        System.out.println("Review : " + member.getReviews());
        System.out.println("Book : " + member.getReviews().get(0).getBook());
        System.out.println("Publisher : " + member.getReviews().get(0).getBook().getPublisher());

    }

//    @Transactional
    @Test
    void bookCascadeTest() {
        Book book = new Book();
        book.setName("JPA 초격차 패키지");

        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");

        book.setPublisher(publisher);
        // cascade = CascadeType.PERSIST 를 연관관계에 적어줌으로써 book을 insert할때 연관된 publisher도 같이 insert해준다.
        // cascade는 작은 폭포라는 의미다. 흐르는것처럼 연관된것이 다 같이 동작하도록 한다.
        bookRepository.save(book);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers: " + publisherRepository.findAll());

        Book book1 = bookRepository.findById(1L).get();

        //name을 변경해서 update했으니 이건 merge에 대한 이벤트이다. 이것도 cascade설정에서 MERGE를 같이 넣어줘야 동작한다.
        book1.getPublisher().setName("슬로우 캠퍼스");
        bookRepository.save(book1);

        System.out.println("publishers : " + publisherRepository.findAll());

    }

    // 멤버를 세팅한다.
    private Member givenMember() {
        return memberRepository.findByEmail("wlsdks12@naver.com");
    }

    // 책을 세팅한다.
    private Book givenBook(Publisher publisher) {
        Book book = new Book();
        book.setName("JPA 초격차 패키지");
        book.setPublisher(publisher);

        return bookRepository.save(book);
    }

    // 출판사를 세팅한다.
    private Publisher givenPublisher() {
        Publisher publisher = new Publisher();
        publisher.setName("패스트캠퍼스");

        return publisherRepository.save(publisher);
    }

    // 리뷰를 세팅한다.
    private void givenReview(Member member, Book book) {
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("너무너무 재미있고 즐거운 책이었어요.");
        review.setScore(5.0f);
        review.setMember(member);
        review.setBook(book);

        reviewRepository.save(review);
    }

    // Book그리고 Review를 세팅한다.
    private void givenBookAndReview() {
        givenReview(givenMember(), givenBook(givenPublisher())); // 리뷰를 저장할 것이다.
    }



}