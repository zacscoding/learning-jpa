package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @GitHub : https://github.com/zacscoding
 */
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update Item i set i.price = i.price * 1.1 where i.stockQuantity < :stockQuantity")
    int bulkPriceUp(@Param("stockQuantity") Integer stockQuantity);
}
