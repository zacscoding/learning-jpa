package jpabook.jpashop.repository;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Repository;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Repository
public class ItemRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Item item) {
        // 저장
        if (item.getId() == null) {
            em.persist(item);
        } else { // 수정
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("SELECT i FROM Item i", Item.class)
            .getResultList();
    }
}
