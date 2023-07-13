package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.listener.Auditable;
import com.fastcampus.jpa.bookmanager.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author jinan
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
//@Table(
//        name = "member",
//        indexes = {@Index(columnList = "name")},
//        uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})} // 복합 컬럼을 만들때 사용함
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

    @OneToMany(fetch    = FetchType.EAGER)
    private List<Address> addresses;
//
    // 영속전에 날짜값을 넣어준다.
//    @PrePersist
//    public void prePersist() {
//        this.createdAt = LocalDateTime.now();
//        this.updatedAt = LocalDateTime.now();
//    }
//    @PreUpdate
//    public void preUpdate() {
//        System.out.println(">>>> preUpdate");
//    }

}
