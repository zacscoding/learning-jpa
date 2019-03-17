package jpabook.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import jpabook.entity.listener.DuckListener;
import lombok.Getter;
import lombok.Setter;

/**
 * https://github.com/holyeye/jpabook
 */
@Getter
@Setter
@Entity
@EntityListeners({DuckListener.class})
public class Duck {

    @Id
    @GeneratedValue
    private Long id;

    @PrePersist
    public void prePersist() {
        System.out.println("Duck.prePersist id : " + id);
    }


    @PostLoad
    public void postLoad() {
        System.out.println("Duck.postLoad : id : " + id);
    }

    @PreRemove
    public void preRemove() {
        System.out.println("Duck.preRemove id : " + id);
    }

    @PostRemove
    public void postRemove() {
        System.out.println("Duck.postRemove id : " + id);
    }

}
