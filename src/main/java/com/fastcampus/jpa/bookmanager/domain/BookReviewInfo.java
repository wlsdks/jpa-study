package com.fastcampus.jpa.bookmanager.domain;

import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true) // 이걸 해줘야 BaseEntity의 생성일자, 업데이트일자를 볼수있다.
@Entity
public class BookReviewInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Book이 있어야만 review가 존재하니 optional=false가 맞다.
    @OneToOne(optional = false) //optional을 false로 주면 반드시 존재하는값이 된다. null을 허용하지 않는다.
    private Book book; //entity를 바로 참조하도록 한다.

    private float averageReviewScore;

    private int reviewCount;


    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BookReviewInfo that = (BookReviewInfo) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
