package jpabook;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import jpabook.model.entity.Item;
import jpabook.model.entity.Member;
import jpabook.model.entity.Order;
import jpabook.model.entity.OrderItem;

/**
 * https://github.com/holyeye/jpabook
 */
public class JpaBookDemoMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = null;

        try {
            emf = Persistence.createEntityManagerFactory("jpabook");
            EntityManager em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();

            Member member1 = new Member();
            member1.setName("User01");
            member1.setCity("seoul");
            member1.setStreet("no");
            member1.setZipcode("111");

            em.persist(member1);

            Item computer = new Item();
            computer.setName("Super-Computer");
            computer.setPrice(10);
            computer.setStockQuantity(5);

            em.persist(computer);
            transaction.commit();

            EntityTransaction transaction2 = em.getTransaction();
            transaction2.begin();

            OrderItem orderItem1 = new OrderItem();
            orderItem1.setCount(1);
            orderItem1.setItem(computer);

            Order firstOrder = new Order();
            firstOrder.setMember(member1);
            firstOrder.setOrderDate(new Date());
            firstOrder.addOrderItem(orderItem1);

            em.persist(firstOrder);
            transaction2.commit();

            Order order = em.find(Order.class, firstOrder.getId());
            OrderItem orderItem = order.getOrderItems().get(0);
            System.out.println(">> " + orderItem.getItem().getName());
        } finally {
            if (emf != null) {
                emf.close();
            }
        }
    }

}
