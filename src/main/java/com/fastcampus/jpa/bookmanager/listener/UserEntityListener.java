package com.fastcampus.jpa.bookmanager.listener;

import com.fastcampus.jpa.bookmanager.domain.Member;
import com.fastcampus.jpa.bookmanager.domain.UserHistory;
import com.fastcampus.jpa.bookmanager.repository.UserHistoryRepository;
import com.fastcampus.jpa.bookmanager.support.BeanUtils;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 * 이렇게 작성하면 자동화된 로직을 구현한다.
 * 여기서는 UserHistory에 자동으로 데이터를 쌓아주는 기능에 대해 작성했다.
 * memberRepository.save(member); 같이 member를 가지고 뭔가를 작업을 하면
 * 이후에 UserHistory에 member값에서 필요한걸 꺼내서 로그성 데이터를 자동으로 쌓아준다.
 */
public class UserEntityListener {

    //db에 저장한 다음 바로 userHistory값을 저장한다.
    @PostPersist // 이렇게 2개 써주면 둘다적용된다.
    @PostUpdate
    public void prePersistAndPreUpdate(Object o) {
        // BeanUtils를 활용해서 빈을 주입받는다.
        UserHistoryRepository userHistoryRepository = BeanUtils.getBean(UserHistoryRepository.class);

        Member member = (Member) o;

        UserHistory userHistory = new UserHistory();
        userHistory.setMemberId(member.getId());
        userHistory.setName(member.getName());
        userHistory.setEmail(member.getEmail());

        userHistoryRepository.save(userHistory);
    }

}
