package com.fastcampus.jpa.bookmanager.listener;

import com.fastcampus.jpa.bookmanager.listener.Auditable;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

public class MyEntityListener {

    //Object 파라미터를 반듯이 받아야만 한다. -> entity를 받아서 처리해야하기 때문임
    @PrePersist
    public void prePersist(Object o) {
        //받은 객체가 Auditable 인스턴스인지 확인한다.
        if (o instanceof Auditable) {
            ((Auditable) o).setCreatedAt(LocalDateTime.now());
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }

    @PreUpdate
    public void preUpdate(Object o) {
        //받은 객체가 Auditable 인스턴스인지 확인한다.
        if (o instanceof Auditable) {
            ((Auditable) o).setUpdatedAt(LocalDateTime.now());
        }
    }

}
