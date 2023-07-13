package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String category;

    private Long authorId;

//    private Long publisherId; -> 아래에 publisher 로 연관관계를 선언해줬으니 중복 id가 안되도록 얘는 삭제한다.

    @ToString.Exclude
    @OneToOne(mappedBy = "book")
    private BookReviewInfo bookReviewInfo;

    // 중간 테이블을 만들지 않기 위함 OneToMany에서 Join할때는 엔티티 자기자신인 Book의 id를 넣어주는듯함
    // 얘를 들면 Review엔티티면 review_id가 되었을것임
    @ToString.Exclude
    @JoinColumn(name = "book_id")
    @OneToMany
    private List<Review> reviews = new ArrayList<>(); // new ArrayList<>();는 npe를 방지하기 위함이다.

    @ToString.Exclude
    @ManyToOne
    private Publisher publisher;

    @ToString.Exclude
    @JoinColumn(name = "book_id")
    @OneToMany
    private List<BookAndAuthor> bookAndAuthors = new ArrayList<>();

    public void addBookAndAuthors(BookAndAuthor... bookAndAuthors) {
        Collections.addAll(this.bookAndAuthors, bookAndAuthors);
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Book book = (Book) o;
        return getId() != null && Objects.equals(getId(), book.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
