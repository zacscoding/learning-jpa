package jpabook.proxy;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.proxy.HibernateProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jpabook.proxy.visitor.PrintVisitor;

/**
 *
 * @GitHub : https://github.com/zacscoding
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProxyTests {

    @PersistenceContext
    EntityManager em;

    @Test
    @Transactional
    public void 영속성컨텍스트와_프록시() {
        ProxyMember member = new ProxyMember("member1", "회원1");
        em.persist(member);
        em.flush();
        em.clear();

        ProxyMember refMember = em.getReference(ProxyMember.class, member.getId());
        // 프록시로 조회된 엔티티에 대해서 같은 엔티티를 찾는 요청이 오면 원본 엔티티가 아닌 처음 조회된
        // 프록시를 반환
        ProxyMember findMember = em.find(ProxyMember.class, member.getId());

        // jpabook.proxy.ProxyMember$HibernateProxy$5iXlIzyk
        assertThat(refMember.getClass().getName()).contains("$");
        // jpabook.proxy.ProxyMember$HibernateProxy$5iXlIzyk
        assertThat(findMember.getClass().getName()).contains("$");
        assertThat(refMember).isEqualTo(findMember);
    }

    @Test
    @Transactional
    public void 영속성컨텍스트와_프록시2() {
        ProxyMember member = new ProxyMember("member1", "회원1");
        em.persist(member);
        em.flush();
        em.clear();

        ProxyMember findMember = em.find(ProxyMember.class, member.getId());
        ProxyMember refMember = em.getReference(ProxyMember.class, member.getId());

        assertThat(findMember.getClass().getName()).contains("ProxyMember");
        assertThat(refMember.getClass().getName()).contains("ProxyMember");
        assertThat(refMember).isEqualTo(findMember);
    }

    @Test
    @Transactional
    public void 프록시_타입비교() {
        ProxyMember member = new ProxyMember("member1", "회원1");
        em.persist(member);
        em.flush();
        em.clear();

        ProxyMember refMember = em.getReference(ProxyMember.class, member.getId());

        assertThat(refMember.getClass() == ProxyMember.class).isFalse();
        assertThat(refMember instanceof ProxyMember).isTrue();
    }

    @Test
    @Transactional
    public void 프록시_동등비교() {
        ProxyMember member = new ProxyMember("member1", "회원1");
        em.persist(member);
        em.flush();
        em.clear();

        ProxyMember refMember = em.getReference(ProxyMember.class, member.getId());

        assertThat(refMember).isEqualTo(member);
    }

    @Test
    @Transactional
    public void 상속관계와_프록시_도메인모델() {
        ProxyBook book = new ProxyBook();
        book.setName("jpabook");
        book.setAuthor("kim");

        em.persist(book);

        ProxyOrderItem orderItem = new ProxyOrderItem();
        orderItem.setItem(book);
        em.persist(orderItem);

        em.flush();
        em.clear();

        ProxyOrderItem find = em.find(ProxyOrderItem.class, orderItem.getId());
        ProxyItem item = find.getItem();

        System.out.println(">> item class : " + item.getClass().getName());
        // ProxyItem에 대한 프록시 이므로 ProxyBook 으로 인스턴스화 X
        assertThat(item.getClass() == ProxyBook.class).isFalse();
        assertThat(item instanceof ProxyBook).isFalse();
        assertThat(item instanceof ProxyItem).isTrue();
    }

    @Test
    @Transactional
    public void JPQL로_대상_직접조회() {
        ProxyBook book = new ProxyBook();
        book.setName("jpabook");
        book.setAuthor("kim");

        em.persist(book);

        ProxyOrderItem orderItem = new ProxyOrderItem();
        orderItem.setItem(book);
        em.persist(orderItem);

        em.flush();
        em.clear();

        ProxyBook findBook = em.createQuery("select b from ProxyBook b where b.id=:bookId", ProxyBook.class)
                               .setParameter("bookId", book.getId())
                               .getSingleResult();

        assertThat(findBook instanceof ProxyBook).isTrue();
        // JPQL로 조회 가능하지만, 다형성을 사용 할 수 없음
    }

    @Test
    @Transactional
    public void 프록시_벗기기() {
        ProxyBook book = new ProxyBook();
        book.setName("jpabook");
        book.setAuthor("kim");

        em.persist(book);

        ProxyOrderItem orderItem = new ProxyOrderItem();
        orderItem.setItem(book);
        em.persist(orderItem);

        em.flush();
        em.clear();

        ProxyOrderItem find = em.find(ProxyOrderItem.class, orderItem.getId());
        ProxyItem item = find.getItem();
        assertThat(item instanceof ProxyBook).isFalse();

        ProxyItem unProxyItem = unProxy(item);
        assertThat(unProxyItem instanceof ProxyBook).isTrue();
    }

    @Test
    @Transactional
    public void 상속관계와_프록시_visitorPattern() {
        ProxyBook book = new ProxyBook();
        book.setName("jpabook");
        book.setAuthor("kim");

        em.persist(book);

        ProxyOrderItem orderItem = new ProxyOrderItem();
        orderItem.setItem(book);
        em.persist(orderItem);

        em.flush();
        em.clear();

        ProxyOrderItem find = em.find(ProxyOrderItem.class, orderItem.getId());
        ProxyItem item = find.getItem();
        item.accept(new PrintVisitor());
    }

    private <T> T unProxy(Object entity) {
        if (entity instanceof HibernateProxy) {
            entity = ((HibernateProxy) entity)
                    .getHibernateLazyInitializer()
                    .getImplementation();
        }

        return (T) entity;
    }
}
