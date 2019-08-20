package jpabook.proxy.visitor;

import jpabook.proxy.ProxyBook;
import jpabook.proxy.ProxyMovie;

/**
 *
 * @GitHub : https://github.com/zacscoding
 */
public class TitleVisitor implements Visitor {

    private String title;

    public String getTitle() {
        return title;
    }

    @Override
    public void visit(ProxyBook book) {
        title = "[제목:" + book.getName() + " 저자:" +
                book.getAuthor() + "]";
    }

    @Override
    public void visit(ProxyMovie movie) {
        title = "[제목:" + movie.getName() + " 감독:" + movie.getDirector() + "]";
    }
}
