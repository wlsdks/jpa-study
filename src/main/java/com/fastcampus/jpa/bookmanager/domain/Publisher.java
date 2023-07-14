package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ToString(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Publisher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // 이 엔티티 자기 자신의 id값을 join할 컬럼명에 넣어주면 된다. 즉, Publisher라는 엔티티니까 publisher_id가 된다.
    // 평상시엔 주로 Many가 연관관계의 주인이라 거기에 JoinColumn이 들어가지만 반대로 지금같은 상황일때는 이렇게 작성한다.
    @ToString.Exclude
    @JoinColumn(name = "publisher_id")
    @OneToMany(orphanRemoval = true) // orphanRemoval : cascade와 다른 고아객체 제거옵션
    private List<Book> books = new ArrayList<>();

    // 요즘 트랜드
    public void addBook(Book book) {
        this.books.add(book);
    }


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Publisher publisher = (Publisher) o;
        return getId() != null && Objects.equals(getId(), publisher.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
