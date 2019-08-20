package jpabook.proxy;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

/**
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@Entity
public class ProxyOrderItem {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_ID")
    private ProxyItem item;

    // 1) 프록시 인터페이스 제공 사용 1)
    public void printItem() {
        System.out.println("TITLE=" + item.getTitle());
    }
}
