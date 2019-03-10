package jpabook.manytomany.direction;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * https://github.com/holyeye/jpabook
 */
public class ManyToManyDirectionDemo {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;
        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em1 = emf.createEntityManager();
            EntityTransaction transaction1 = em1.getTransaction();
            transaction1.begin();
            Product06 product = new Product06();
            product.setId("productA");
            product.setName("상품A");
            em1.persist(product);

            Member06 member1 = new Member06();
            member1.setId("member01");
            member1.setUsername("회원1");
            member1.getProducts().add(product);
            em1.persist(member1);
            transaction1.commit();

            em1.close();

            EntityManager em2 = emf.createEntityManager();
            EntityTransaction transaction2 = em2.getTransaction();
            transaction2.begin();
            Member06 find = em2.find(Member06.class, "member01");
            List<Product06> products = find.getProducts();
            for (Product06 p : products) {
                System.out.println("product.name = " + p.getName());
            }
            transaction2.commit();

//            Output
//            Hibernate: /* insert jpabook.manytomany.direction.Product06 */ insert into product06 (name, product06_id) values (?, ?)
//            Hibernate: /* insert jpabook.manytomany.direction.Member06 */ insert into member06 (username, member06_id) values (?, ?)
//            Hibernate: /* insert collection row jpabook.manytomany.direction.Member06.products */ insert into member_product (member06_id, product06_id) values (?, ?)
//            Hibernate: select member06x0_.member06_id as member1_6_0_, member06x0_.username as username2_6_0_ from member06 member06x0_ where member06x0_.member06_id=?
//            Hibernate: select products0_.member06_id as member1_6_0_, products0_.product06_id as product2_7_0_, product06x1_.product06_id as product1_8_1_, product06x1_.name as name2_8_1_ from member_product products0_ inner join product06 product06x1_ on products0_.product06_id=product06x1_.product06_id where products0_.member06_id=?
//            product.name = 상품A

        } finally {
            if (emf != null) {
                emf.close();
            }
        }

    }
}
