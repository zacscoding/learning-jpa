package jpabook.proxy;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import jpabook.proxy.visitor.Visitor;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @GitHub : https://github.com/zacscoding
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("M")
public class ProxyMovie extends ProxyItem {

    private String director;
    private String actor;

    @Override
    public String getTitle() {
        return "[제목: " + getName() + "감독: " + director + " 배우:" + actor + "]";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
