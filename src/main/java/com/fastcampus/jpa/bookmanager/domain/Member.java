package com.fastcampus.jpa.bookmanager.domain;

import com.fastcampus.jpa.bookmanager.listener.Auditable;
import com.fastcampus.jpa.bookmanager.listener.UserEntityListener;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    // EAGER가 2개 이상이면 List타입은 1개만 있어야 한다. 즉, EAGER를 하나 빼거나 LIST를 하나는 SET으로 선언해야 된다.
    @OneToMany(fetch = FetchType.LAZY)
    private List<Address> addresses;

    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    @OneToMany(fetch = FetchType.EAGER)
    private Set<UserHistory> memberHistories;


}
