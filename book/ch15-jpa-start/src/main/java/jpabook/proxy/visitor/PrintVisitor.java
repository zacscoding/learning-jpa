package jpabook.proxy.visitor;

import jpabook.proxy.ProxyBook;
import jpabook.proxy.ProxyMovie;

/**
 *
 * @GitHub : https://github.com/zacscoding
 */
public class PrintVisitor implements Visitor {
    @Override
    public void visit(ProxyBook book) {
        System.out.println("book.class=" + book.getClass());
        System.out.println("[PrintVisitor] [제목:" + book.getName()
                           + " 저자:" + book.getAuthor() + "]");
    }

    @Override
    public void visit(ProxyMovie movie) {
        System.out.println("book.class=" + movie.getClass());
        System.out.println("[PrintVisitor] [제목:" + movie.getName()
                           + " 감독:" + movie.getDirector() + "]");
    }
}
