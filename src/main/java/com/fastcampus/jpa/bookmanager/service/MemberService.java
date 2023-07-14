package com.fastcampus.jpa.bookmanager.service;

import com.fastcampus.jpa.bookmanager.domain.Member;
import com.fastcampus.jpa.bookmanager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
public class MemberService {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void put() {
        Member member = new Member();
        member.setName("newMember");
        member.setEmail("newMember@naver.com");

//        memberRepository.save(member);
        entityManager.persist(member);
        entityManager.detach(member);

        member.setName("newMemberAfterPersist");
        entityManager.merge(member);
        entityManager.flush();
        entityManager.clear();

        entityManager.remove(member);
    }

}
