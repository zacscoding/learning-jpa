package jpabook.entity.listener;

import javax.persistence.PostPersist;

/**
 * https://github.com/holyeye/jpabook
 */
public class DuckListener {

    @PostPersist
    public void postPersist(Object object) {
        System.out.println("postPersist object : " + object.getClass());
    }
}
