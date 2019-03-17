package jpabook;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityGraph;
import jpabook.entity.Order;

/**
 * https://github.com/holyeye/jpabook
 */
public class GraphDemo {

    public static void main(String[] args) {
        AbstractDemoRunner runner = new AbstractDemoRunner();
        try {
            runner.doTask("em.find() with entity graph", em -> {
                EntityGraph graph = em.getEntityGraph("Order.withMember");

                Map hints = new HashMap();
                hints.put("javax.persistence.fetchgraph", graph);

                Order order = em.find(Order.class, 1L, hints);

//                ---------------------------- em.find() with entity graph ----------------------------
//                    Hibernate: select order0_.order_id as order_id1_4_0_, order0_.member_id as member_i2_4_0_, member1_.member_id as member_i1_3_1_, member1_.age as age2_3_1_, member1_.team as team5_3_1_, member1_.name as name3_3_1_, member1_.vip as vip4_3_1_ from orders order0_ inner join member member1_ on order0_.member_id=member1_.member_id where order0_.order_id=?
//                ---------------------------------------------------------------------------
            });

            runner.doTask("subgraph", em -> {
                Map hints = new HashMap();
                hints.put("javax.persistence.fetchgraph", em.getEntityGraph("Order.withAll"));

                Order order = em.find(Order.class, 1L, hints);

//                ---------------------------- subgraph----------------------------
//                Hibernate: select order0_.order_id as order_id1_6_0_, order0_.member_id as member_i2_6_0_, member1_.member_id as member_i1_4_1_, member1_.age as age2_4_1_, member1_.team as team5_4_1_, member1_.name as name3_4_1_, member1_.vip as vip4_4_1_, orderitems2_.order_id as order_id3_6_2_, orderitems2_.id as id1_5_2_, orderitems2_.id as id1_5_3_, orderitems2_.item_id as item_id2_5_3_, orderitems2_.order_id as order_id3_5_3_, item3_.id as id1_3_4_ from orders order0_ inner join member member1_ on order0_.member_id=member1_.member_id left outer join order_item orderitems2_ on order0_.order_id=orderitems2_.order_id left outer join item item3_ on orderitems2_.item_id=item3_.id where order0_.order_id=?
//                ---------------------------------------------------------------------------
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            runner.close();
        }
    }
}
