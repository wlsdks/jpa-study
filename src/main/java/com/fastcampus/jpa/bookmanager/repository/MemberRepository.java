package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jinan
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

}
