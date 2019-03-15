package jpabook.jpashop.service;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @GitHub : https://github.com/zacscoding
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:appConfig.xml")
@Transactional
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void 저장성공() {
        // given
        Item item = new Book();
        item.setName("book001");

        // when
        itemService.saveItem(item);

        // then
        assertThat(item, is(itemRepository.findOne(item.getId())));
    }

    @Test
    public void 조회() {
        // given
        Item[] items = new Item[5];
        for (int i = 0; i < 5; i++) {
            items[i] = new Book();
            items[i].setName("item" + i);
            itemRepository.save(items[i]);
        }

        // when then
        assertThat(itemService.findItems().size(), is(5));
        assertThat(itemService.findOne(items[1].getId()), is(items[1]));
    }

}
