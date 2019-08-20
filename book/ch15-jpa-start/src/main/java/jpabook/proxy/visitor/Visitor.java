package jpabook.proxy.visitor;

import jpabook.proxy.ProxyBook;
import jpabook.proxy.ProxyMovie;

/**
 *
 * @GitHub : https://github.com/zacscoding
 */
public interface Visitor {

    void visit(ProxyBook book);

    void visit(ProxyMovie movie);
}
