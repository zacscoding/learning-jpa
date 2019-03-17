package jpabook;

import jpabook.AbstractDemoRunner;
import jpabook.entity.Duck;

/**
 * https://github.com/holyeye/jpabook
 */
public class ListenerDemo {

    public static void main(String[] args) {
        AbstractDemoRunner runner = new AbstractDemoRunner();
        try {
            runner.doTask("Listener demo..", em -> {
                Duck duck = new Duck();
                em.persist(duck);
                em.remove(duck);

//                ---------------------------- Listener demo..----------------------------
//                    Duck.prePersist id : null
//                Hibernate: call next value for hibernate_sequence
//                Duck.preRemove id : 1
//                Hibernate: /* insert jpabook.entity.Duck */ insert into duck (id) values (?)
//                postPersist object : class jpabook.entity.Duck
//                Hibernate: /* delete jpabook.entity.Duck */ delete from duck where id=?
//                Duck.postRemove id : 1
//                    ---------------------------------------------------------------------------
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            runner.close();
        }
    }
}
