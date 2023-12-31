package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import com.fastcampus.jpa.bookmanager.domain.Member;
import com.fastcampus.jpa.bookmanager.domain.Publisher;
import com.fastcampus.jpa.bookmanager.domain.Review;
import com.fastcampus.jpa.bookmanager.dto.BookStatus;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import java.time.LocalDateTime;
import java.util.List;

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

    @Test
    void bookRemoveCascadeTest() {
//        bookRepository.deleteById(1L);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers " + publisherRepository.findAll());

        bookRepository.findAll().forEach(book -> System.out.println(book.getPublisher()));

        // remove도 cascade로 묶어주면 연관된것이 같이 지워진다.
        Book book2 = bookRepository.findById(1L).get();
//        bookRepository.delete(book2);
//        bookRepository.deleteById(1L);

//        publisherRepository.delete(book2.getPublisher());

        Book book3 = bookRepository.findById(1L).get();
        book3.setPublisher(null);

        bookRepository.save(book3);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers: " + publisherRepository.findAll());
        System.out.println("book3-publisher: " + bookRepository.findById(1L).get().getPublisher());

    }

    @DisplayName("flag를 활용한 soft delete방식 - 현업에서 가장 많이 사용")
    @Test
    void softDelete() {
        bookRepository.findAll().forEach(System.out::println);
        System.out.println(bookRepository.findById(3L));

        bookRepository.findByCategoryIsNull().forEach(System.out::println);

//        bookRepository.findAllByDeletedFalse().forEach(System.out::println);
//        bookRepository.findByCategoryIsNullAndDeletedFalse().forEach(System.out::println);
    }

    @DisplayName("@Query 테스트")
    @Test
    void queryTest() {
        bookRepository.findAll().forEach(System.out::println);

        System.out.println("findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual : " +
                bookRepository.findByCategoryIsNullAndNameEqualsAndCreatedAtGreaterThanEqualAndUpdatedAtGreaterThanEqual(
                        "JPA 초격차 패키지",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)
                ));

        // 가독성을 높인 코드
        System.out.println("findByNameRecently : " +
                bookRepository.findByNameRecently(
                        "JPA 초격차 패키지",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L))
        );

        // tuple타입으로 반환받을수가 있다.
        System.out.println(bookRepository.findBookNameAndCategory());

        // tuple안에서 0번,1번을 꺼내서 볼수가 있다.
        bookRepository.findBookNameAndCategory().forEach(b -> System.out.println(b.getName() + " : " + b.getCategory()));

        bookRepository.findBookNameAndCategory(PageRequest.of(0, 1)).forEach(
                bookNameAndCategory -> System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory())
        );

    }

    @DisplayName("Native Query 테스트")
    @Test
    void nativeQueryTest() {
//        bookRepository.findAll().forEach(System.out::println);
//        bookRepository.findAllCustom().forEach(System.out::println);

        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            book.setCategory("IT전문서");
        }
        bookRepository.saveAll(books);

        System.out.println(bookRepository.findAll());

        System.out.println("affected rows : " + bookRepository.updateCategories());
        bookRepository.findAllCustom().forEach(System.out::println);

        System.out.println(bookRepository.showTables());
    }

    @DisplayName("Converter 테스트")
    @Test
    void converterTest() {
        bookRepository.findAll().forEach(System.out::println);

        Book book = new Book();
        book.setName("또다른 IT전문서적");
        book.setStatus(new BookStatus(200));

        bookRepository.save(book);

        System.out.println(bookRepository.findRawRecord().values());

        bookRepository.findAll().forEach(System.out::println);
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