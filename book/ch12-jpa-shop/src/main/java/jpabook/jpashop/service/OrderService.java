package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderSearch;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Service
public class OrderService {

    private MemberRepository memberRepository;
    private OrderRepository orderRepository;
    private ItemService itemService;

    @Autowired
    public OrderService(MemberRepository memberRepository, OrderRepository orderRepository,
        ItemService itemService) {
        this.memberRepository = memberRepository;
        this.orderRepository = orderRepository;
        this.itemService = itemService;
    }

    /**
     * 주문
     */
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemService.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery(member.getAddress());
        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(
            item, item.getPrice(), count
        );
        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     */
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        // 주문 취소
        order.cancel();
    }

    /**
     * 주문 검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch.toSpecification());
    }
}
