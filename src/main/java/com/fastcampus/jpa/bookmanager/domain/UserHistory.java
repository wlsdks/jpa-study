package com.fastcampus.jpa.bookmanager.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.*;
import java.util.Objects;

@Data
@ToString(callSuper = true)
@NoArgsConstructor
@Entity
public class UserHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    // 이렇게 안적으면 Enum이 0,1,2 이런식으로 들어가니 유의해서 String으로 만들자.
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // @Embeddable한 클래스를 받았다는 의미인 @Embedded를 적어준다.
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "home_city")),
            @AttributeOverride(name = "district", column = @Column(name = "home_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "home_address_detail")), // 기존 @Column의 컬럼명은 이걸로 재정의된다.
            @AttributeOverride(name = "zipCode", column = @Column(name = "home_zipCode"))
    })
    @Embedded
    private Address homeAddress;

    // 이렇게 embedded로 같은것을 받고 @AttributeOverrides로 설정을 해주고 필드명을 각각 다르게 해서 재사용이 가능하다.
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "company_city")),
            @AttributeOverride(name = "district", column = @Column(name = "company_district")),
            @AttributeOverride(name = "detail", column = @Column(name = "company_address_detail")), // 기존 @Column의 컬럼명은 이걸로 재정의된다.
            @AttributeOverride(name = "zipCode", column = @Column(name = "company_zipCode"))
    })
    @Embedded
    private Address companyAddress;

    @ManyToOne
    private Member member;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserHistory that = (UserHistory) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }
}
