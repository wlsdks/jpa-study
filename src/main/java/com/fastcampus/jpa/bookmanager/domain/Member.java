package com.fastcampus.jpa.bookmanager.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jinan
 */
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
@Table(name = "member")
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}