package jpabook.entity.expression;

import com.mysema.query.annotations.QueryDelegate;
import com.mysema.query.types.expr.BooleanExpression;
import jpabook.entity.Product;
import jpabook.entity.QProduct;

/**
 * @GitHub : https://github.com/zacscoding
 */
public class ItemExpression {

    @QueryDelegate(Product.class)
    public static BooleanExpression isExpensive(QProduct product, Integer price) {
        return product.price.gt(price);
    }
}
