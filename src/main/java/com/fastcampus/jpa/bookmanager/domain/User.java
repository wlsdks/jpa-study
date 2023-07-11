package com.fastcampus.jpa.bookmanager.domain;

import lombok.*;

import javax.persistence.Entity;
import java.time.LocalDateTime;

/**
 * @author jinan
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
public class User {

    @NonNull
    private String name;
    @NonNull
    private String email;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
