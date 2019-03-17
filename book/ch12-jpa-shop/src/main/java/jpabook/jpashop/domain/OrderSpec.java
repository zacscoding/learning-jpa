package jpabook.jpashop.domain;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

/**
 * https://github.com/holyeye/jpabook
 */
public class OrderSpec {

    public static Specification<Order> memberNameLike(final String memberName) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {

                if (StringUtils.isEmpty(memberName)) {
                    return null;
                }

                Join<Order, Member> m = root.join("member", JoinType.INNER);

                return criteriaBuilder.like(m.<String>get("name"), "%" + memberName + "%");
            }
        };
    }

    public static Specification<Order> orderStatusEq(final OrderStatus orderStatus) {
        return new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) {

                if (orderStatus == null) {
                    return null;
                }

                return criteriaBuilder.equal(root.get("status"), orderStatus);
            }
        };
    }

}