package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.listener.UserEntityListener;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author jinan
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
//@Builder
@ToString(callSuper = true)
//@Table(
//        name = "member"
////        indexes = {@Index(columnList = "name")},
////        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})} // 복합 컬럼을 만들때 사용함
//)
@EntityListeners(value = UserEntityListener.class)
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    // 이렇게 안적으면 Enum이 0,1,2 이런식으로 들어가니 유의해서 String으로 만들자.
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(updatable = false) // update에서 제외시킨다.
    @CreatedDate
    private LocalDateTime createdAt;

    @ToString.Exclude
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    @OneToMany(fetch = FetchType.EAGER)
    private List<UserHistory> memberHistories;

    @ToString.Exclude
    @JoinColumn(name = "member_id") // 이렇게해야 중간 테이블이 사라진다.
    @OneToMany // OneToMany는 List로 선언될것이다.
    private List<Review> reviews = new ArrayList<>(); // npe방지 빈 arrayList선언

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Member member = (Member) o;
        return getId() != null && Objects.equals(getId(), member.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
