package jpabook.jpashop.repository;

import static org.junit.Assert.assertTrue;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * https://github.com/holyeye/jpabook
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void setUp() {
        Item item01 = new Book();
        item01.setName("book01");
        item01.setPrice(100);
        item01.setStockQuantity(100);
        itemRepository.save(item01);

        Item item02 = new Book();
        item02.setName("book02");
        item02.setPrice(100);
        item02.setStockQuantity(100);
        itemRepository.save(item02);

        Item item03 = new Book();
        item03.setName("book03");
        item03.setPrice(100);
        item03.setStockQuantity(200);
        itemRepository.save(item03);
    }

    @Test
    public void updateBulkPrice() {
        // when
        int updated = itemRepository.bulkPriceUp(200);

        // then
        assertTrue(updated == 2);
        for (Item item : itemRepository.findAll()) {
            if (item.getStockQuantity() < 200) {
                assertTrue(item.getPrice() == 110);
            } else {
                assertTrue(item.getPrice() == 100);
            }
        }
    }
}
