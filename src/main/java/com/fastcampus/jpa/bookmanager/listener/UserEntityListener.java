package com.fastcampus.jpa.bookmanager.listener;

import com.fastcampus.jpa.bookmanager.domain.Member;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import com.fastcampus.jpa.bookmanager.repository.UserHistoryRepository;
import com.fastcampus.jpa.bookmanager.support.BeanUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class UserEntityListener {

    @PrePersist // 이렇게 2개 써주면 둘다적용된다.
    @PreUpdate
    public void prePersistAndPreUpdate(Object o) {
        // BeanUtils를 활용해서 빈을 주입받는다.
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);

        Member member = (Member) o;

        UserHistory userHistory = new UserHistory();
        userHistory.setUserId(member.getId());
        userHistory.setName(member.getName());
        userHistory.setEmail(member.getEmail());

        userHistoryRepository.save(userHistory);
    }

}
