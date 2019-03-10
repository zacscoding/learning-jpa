package jpabook.collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class CollectionDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;

        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em1 = emf.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();

            Member03 member = new Member03();

            // 임베디드 값 타입
            member.setHomeAddress(new Address03("통영", "몽돌해수욕장", "660-123"));

            // 기본값 타입 컬렉션
            member.getFavoriteFoods().add("짬뽕");
            member.getFavoriteFoods().add("짜장");
            member.getFavoriteFoods().add("탕수육");

            // 임베디드 값 타입 컬렉션
            member.getAddressHistory().add(new Address03("서울", "강남", "123-123"));
            member.getAddressHistory().add(new Address03("서울", "강북", "000-000"));

            em1.persist(member);
            transaction1.commit();

//            insert into member03 (city, street, zipcode, member03_id) values (?, ?, ?, ?)
//            insert into address03 (member03_id, city, street, zipcode) values (?, ?, ?, ?)
//            insert into address03 (member03_id, city, street, zipcode) values (?, ?, ?, ?)
//            insert into favorite_foods (member03_id, food_name) values (?, ?)
//            insert into favorite_foods (member03_id, food_name) values (?, ?)
//            insert into favorite_foods (member03_id, food_name) values (?, ?)
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}