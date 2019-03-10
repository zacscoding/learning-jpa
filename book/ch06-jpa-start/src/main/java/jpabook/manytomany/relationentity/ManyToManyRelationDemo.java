package jpabook.manytomany.relationentity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class ManyToManyRelationDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em1 = emf.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();
            // 회원 저장
            Member08 member = new Member08();
            member.setId("member1");
            member.setUsername("회원1");
            em1.persist(member);

            // 상품 저장
            Product08 product = new Product08();
            product.setId("productA");
            product.setName("상품1");
            em1.persist(product);

            // 회원상품 저장
            Member08Product08 memberProduct = new Member08Product08();
            memberProduct.setMember(member);
            memberProduct.setProduct(product);
            memberProduct.setOrderAmount(2);
            em1.persist(memberProduct);
            transaction1.commit();
            em1.close();

            // 검색
            EntityManager em2 = emf.createEntityManager();

            Member08Product08Id memberProductId = new Member08Product08Id();
            memberProductId.setMember(member.getId());
            memberProductId.setProduct(product.getId());
            Member08Product08 find = em2.find(Member08Product08.class, memberProductId);

            System.out.println("member :: " + find.getMember().getUsername());
            System.out.println("product :: " + find.getProduct().getName());
            System.out.println("orderAmount :: " + find.getOrderAmount());

//            Output ::
//            Hibernate: /* insert jpabook.manytomany.relationentity.Member08 */ insert into member08 (username, member08_id) values (?, ?)
//            Hibernate: /* insert jpabook.manytomany.relationentity.Product08 */ insert into product08 (name, product08_id) values (?, ?)
//            Hibernate: /* insert jpabook.manytomany.relationentity.Member08Product08 */ insert into member08product08 (order_amount, member08_id, product08_id) values (?, ?, ?)
//            Hibernate: select member08pr0_.member08_id as member1_11_0_, member08pr0_.product08_id as product2_11_0_, member08pr0_.order_amount as order_am3_11_0_, member08x1_.member08_id as member1_10_1_, member08x1_.username as username2_10_1_, product08x2_.product08_id as product1_14_2_, product08x2_.name as name2_14_2_ from member08product08 member08pr0_ inner join member08 member08x1_ on member08pr0_.member08_id=member08x1_.member08_id inner join product08 product08x2_ on member08pr0_.product08_id=product08x2_.product08_id where member08pr0_.member08_id=? and member08pr0_.product08_id=?
//            member :: 회원1
//            product :: 상품1
//            orderAmount :: 2
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }
}
