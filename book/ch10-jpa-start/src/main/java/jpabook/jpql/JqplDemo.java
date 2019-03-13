package jpabook.jpql;

import com.mysema.query.QueryModifiers;
import com.mysema.query.SearchResults;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;
import com.mysema.query.types.Projections;
import com.mysema.query.types.QTuple;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import jpabook.AbstractDemoRunner;
import jpabook.dto.MemberDTO;
import jpabook.dto.ProductDTO;
import jpabook.entity.Address;
import jpabook.entity.Member;
import jpabook.entity.Product;
import jpabook.entity.QMember;
import jpabook.entity.QOrder;
import jpabook.entity.QProduct;
import jpabook.entity.Team;

/**
 * https://github.com/holyeye/jpabook
 */
public class JqplDemo {

    public static void main(String[] args) {
        final AbstractDemoRunner runner = new AbstractDemoRunner();

        try {
            final Team[] teams = new Team[]{
                runner.saveTeam(new Team(null, "team01", null)),
                runner.saveTeam(new Team(null, "team02", null)),
                runner.saveTeam(new Team(null, "team03", null))
            };

            IntStream.rangeClosed(1, 5).forEach(i -> {
                Team team = teams[new Random().nextInt(teams.length)];
                runner.saveMember(
                    new Member(null, "유저" + i, i, team, null)
                );
            });

            // defaultApi(runner);
            // parameterBinding(runner);
            // projection(runner);
            // pagingAPI(runner);
            // setAndSort(runner);
            // reportingQuery(runner);
            // jpqlJoin(runner);
            // pathExpression(runner);
            // subquery(runner);
            // useEntityDirectly(runner);
            // namedQuery(runner);
            // criteriaUsage(runner);
            // criteriaQuery(runner);
            // set(runner);
            // subquery2(runner);
            // dynamicQuery(runner);
            // queryDSL(runner);
            nativeSQL(runner);
        } finally {
            runner.close();
        }
    }

    private static void nativeSQL(AbstractDemoRunner runner) {
        try {
            runner.doTask("엔티티 조회", em -> {
                String sql = "SELECT MEMBER_ID, AGE, NAME FROM MEMBER WHERE AGE > ?";
                Query nativeQuery = em.createNativeQuery(sql, Member.class)
                    .setParameter(1, 3);

                List<Member> results = nativeQuery.getResultList();
                System.out.println("Search result : " + results.size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void queryDSL(AbstractDemoRunner runner) {
        try {
            runner.doTask("QueryDSL 시작", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_
                // from member member0_ where member0_.name=? order by member0_.name desc
                JPAQuery query = new JPAQuery(em);
                QMember qMember = new QMember("m"); // 생성되는 JPQL의 별칭이 m

                List<Member> results = query.from(qMember)
                    .where(qMember.username.eq("회원1"))
                    .orderBy(qMember.username.desc())
                    .list(qMember);

                System.out.println("Search result size :: " + results.size());
            });

            runner.doTask("검색 조건 쿼리", em -> {
                JPAQuery query = new JPAQuery(em);
                QProduct product = QProduct.product;

                List<Product> products = query.from(product)
                    .where(product.name.eq("좋은 상품").and(product.price.gt(20000))
                    )
                    // .orderBy(product.price.desc(), product.stockAmount.asc())
                    // .offset(10).limit(20)
                    .list(product);

                /*
                product.price.between(10000, 20000); // 가격이 10000 ~ 20000
                product.name.concat("상품1"); // 상품1 을 포함 like '%상품1%'
                product.name.startsWith("고급"); // like '고급%'
                */

                System.out.println("Search reseult :: " + products.size());
            });

            runner.doTask("페이징과 정렬", em -> {
                JPAQuery query = new JPAQuery(em);
                QueryModifiers queryModifiers = new QueryModifiers(20L, 10L); // limit offset

                List<Product> products = query.from(QProduct.product)
                    .restrict(queryModifiers)
                    .list(QProduct.product);

                System.out.println("Search size :: " + products.size());
            });

            runner.doTask("페이징과 정렬2", em -> {
                JPAQuery query = new JPAQuery(em);
                SearchResults<Product> results = query.from(QProduct.product)
                    .where(QProduct.product.price.gt(10000))
                    .offset(10L).limit(20L)
                    .listResults(QProduct.product);

                long total = results.getTotal();
                long limit = results.getLimit();
                long offset = results.getOffset();

                System.out.printf("total : %d / limit : %d / offset : %d / contents : %d\n"
                    , total, limit, offset, results.getResults().size());
            });

            runner.doTask("그룹", em -> {
                // select product0_.product_id as product_1_2_, product0_.name as name2_2_, product0_.order_id as order_id5_2_, product0_.price as price3_2_, product0_.stock_amount as stock_am4_2_
                // from product product0_ group by product0_.price having product0_.price>?
                JPAQuery query = new JPAQuery(em);
                QProduct product = QProduct.product;
                List<Product> results = query.from(QProduct.product)
                    .groupBy(product.price)
                    .having(product.price.gt(1000))
                    .list(product);

                System.out.println("Search result size : " + results.size());
            });

            runner.doTask("조인", em -> {
                boolean temp = true;
                if (temp) {
                    return;
                }

                JPAQuery query = new JPAQuery(em);
                QOrder order = QOrder.order;
                QMember member = QMember.member;
                QProduct product = QProduct.product;

                // 기본 조인
                query.from(order)
                    .join(order.member, member)
                    .leftJoin(order.products, product)
                    .list(order);

                // 조인 on 사용
                query.from(order)
                    .leftJoin(order.products, product)
                    .on(product.count().gt(2))
                    .list(order);

                // fetch 조인
                query.from(order)
                    .innerJoin(order.member, member).fetch()
                    .leftJoin(order.products, product).fetch()
                    .list(order);

                // from 절에 여러 조건 사용
                query.from(order, member)
                    .where(order.member.eq(member))
                    .list(order);
            });

            runner.doTask("서브 쿼리", em -> {
                boolean temp = true;
                if (temp) {
                    return;
                }

                QProduct product = QProduct.product;
                QProduct productSub = new QProduct("productSub");

                JPAQuery query = new JPAQuery(em);
                query.from(product)
                    .where(product.price.eq(
                        new JPASubQuery().from(productSub).unique(productSub.price.max())
                    ))
                    .list(product);

                query.from(product)
                    .where(product.in(
                        new JPASubQuery().from(productSub)
                            .where(product.name.eq(productSub.name))
                            .list(productSub)
                    ))
                    .list(product);
            });

            runner.doTask("프로젝션 결과 반환 : 프로젝션 대상이 하나", em -> {
                JPAQuery query = new JPAQuery(em);
                QProduct product = QProduct.product;
                List<String> results = query.from(product).list(product.name);
                System.out.println("Search result : " + results.size());
            });

            runner.doTask("프로젝션 결과 반환 : 여러 컬럼 반환과 튜플", em -> {
                JPAQuery query = new JPAQuery(em);
                QProduct product = QProduct.product;

                List<com.mysema.query.Tuple> results = query.from(product).list(product.name, product.price);
                // List<com.mysema.query.Tuple> results = query.from(product).list(new QTuple(product.name, product.price));

                System.out.println("Search result : " + results.size());
                for (com.mysema.query.Tuple tuple : results) {
                    System.out.println("name = " + tuple.get(product.name));
                    System.out.println("price = " + tuple.get(product.price));
                }
            });

            runner.doTask("프로젝션 결과 반환 : 빈 생성", em -> {
                boolean temp = true;
                if (temp) {
                    return;
                }

                JPAQuery query = new JPAQuery(em);
                QProduct product = QProduct.product;

                // 프로퍼티 접근(setter)
                List<ProductDTO> results = query.from(product)
                    .list(Projections.bean(ProductDTO.class, product.name.as("username"), product.price));

                // 필드 직접 접근
                query.from(product)
                    .list(Projections.fields(ProductDTO.class, product.name.as("username"), product.price));

                // 생성자 사용
                query.from(product)
                    .list(Projections.constructor(ProductDTO.class, product.name, product.price));
                System.out.println("Search result : " + results.size());
            });

            runner.doTask("수정 배치 쿼리", em -> {
                QProduct product = QProduct.product;
                JPAUpdateClause updateClause = new JPAUpdateClause(em, product);
                long count = updateClause.where(product.name.eq("시골개발자의 JPA 책"))
                    .set(product.price, product.price.add(100))
                    .execute();
            });

            runner.doTask("삭제 배치 쿼리", em -> {
                QProduct product = QProduct.product;
                JPADeleteClause deleteClause = new JPADeleteClause(em, product);
                long count = deleteClause.where(product.name.eq("시골개발자의 JPA 책"))
                    .execute();
            });

            runner.doTask("동적 쿼리", em -> {
                // select product0_.product_id as product_1_2_, product0_.name as name2_2_, product0_.order_id as order_id5_2_, product0_.price as price3_2_, product0_.stock_amount as stock_am4_2_
                // from product product0_ where product0_.price>?
                JPAQuery query = new JPAQuery(em);
                QProduct product = QProduct.product;

                List<Product> results = query.from(product)
                    .where(product.isExpensive(3000))
                    .list(product);

                System.out.println("Search result : " + results.size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void dynamicQuery(AbstractDemoRunner runner) {
        try {
            runner.doTask("JPQL 동적 쿼리", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ inner join team team1_ on member0_.team=team1_.team_id where member0_.age=? and team1_.name=?
                // 검색 조건
                Integer age = 10;
                String username = null;
                String teamName = "team01";

                // JQPL 동적 쿼리 생성
                StringBuilder jpql = new StringBuilder("SELECT m from Member m join m.team t ");
                List<String> criteria = new ArrayList<>();

                if (age != null) {
                    criteria.add(" m.age = :age ");
                }

                if (username != null) {
                    criteria.add(" m.username = :username ");
                }

                if (teamName != null) {
                    criteria.add(" t.name = :teamName ");
                }

                if (criteria.size() > 0) {
                    jpql.append(" where ");
                }

                for (int i = 0; i < criteria.size(); i++) {
                    if (i > 0) {
                        jpql.append(" and ");
                    }
                    jpql.append(criteria.get(i));
                }

                TypedQuery<Member> query = em.createQuery(jpql.toString(), Member.class);
                if (age != null) {
                    query.setParameter("age", age);
                }

                if (username != null) {
                    query.setParameter("username", username);
                }

                if (teamName != null) {
                    query.setParameter("teamName", teamName);
                }

                List<Member> results = query.getResultList();
                System.out.println(">> Search result : " + results.size());
            });

            runner.doTask("Criteria 동적 쿼리", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_
                // from member member0_ inner join team team1_ on member0_.team=team1_.team_id
                // where member0_.age=? and team1_.name=?
                // 검색 조건
                Integer age = 10;
                String username = null;
                String teamName = "team01";

                // Criteria 동적 쿼리 생성
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Member> cq = cb.createQuery(Member.class);

                Root<Member> m = cq.from(Member.class);
                Join<Member, Team> t = m.join("team");

                List<Predicate> criteria = new ArrayList<>();
                if (age != null) {
                    criteria.add(cb.equal(m.<Integer>get("age"), cb.parameter(Integer.class, "age")));
                }

                if (username != null) {
                    criteria.add(cb.equal(m.get("username"), cb.parameter(String.class, "username")));
                }

                if (teamName != null) {
                    criteria.add(cb.equal(t.get("name"), cb.parameter(String.class, "teamName")));
                }

                cq.where(cb.and(criteria.toArray(new Predicate[0])));

                TypedQuery<Member> query = em.createQuery(cq);
                if (age != null) {
                    query.setParameter("age", age);
                }

                if (username != null) {
                    query.setParameter("username", username);
                }

                if (teamName != null) {
                    query.setParameter("teamName", teamName);
                }

                List<Member> results = query.getResultList();
                System.out.println("Search result : " + results.size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void subquery2(AbstractDemoRunner runner) {
        try {
            runner.doTask("서브쿼리", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.age>=(select avg(cast(member1_.age as double)) from member member1_)
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Member> mainQuery = cb.createQuery(Member.class);

                // 서브 쿼리 생성
                Subquery<Double> subQuery = mainQuery.subquery(Double.class);

                Root<Member> m2 = subQuery.from(Member.class);
                subQuery.select(cb.avg(m2.<Integer>get("age")));

                // 메인 쿼리 생성
                Root<Member> m = mainQuery.from(Member.class);
                mainQuery.select(m).where(cb.ge(m.<Integer>get("age"), subQuery));

                TypedQuery<Member> query = em.createQuery(mainQuery);
                List<Member> results = query.getResultList();

                System.out.println("Search result : " + results.size());
            });

            runner.doTask("IN 식", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.name in (? , ?)
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Member> cq = cb.createQuery(Member.class);
                Root<Member> m = cq.from(Member.class);

                cq.select(m)
                    .where(cb.in(m.get("username"))
                        .value("회원1")
                        .value("회원2")
                    );

                TypedQuery<Member> query = em.createQuery(cq);
                List<Member> results = query.getResultList();

                System.out.println("Search result : " + results.size());
            });

            runner.doTask("CASE 식", em -> {
                // select member0_.name as col_0_0_, case when member0_.age>=60 then 600 when member0_.age<=15 then 500 else 1000 end as col_1_0_ from member member0_
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
                Root<Member> m = cq.from(Member.class);

                cq.multiselect(
                    m.get("username"),
                    cb.selectCase()
                        .when(cb.ge(m.<Integer>get("age"), 60), 600)
                        .when(cb.le(m.<Integer>get("age"), 15), 500)
                        .otherwise(1000)
                );

                TypedQuery<Object[]> query = em.createQuery(cq);
                List<Object[]> results = query.getResultList();
                System.out.println("Search result : " + results.size());
                for (Object[] result : results) {
                    System.out.println(Arrays.toString(result));
                }
            });

            runner.doTask("파라미터 정의", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_
                // from member member0_ where member0_.name=?
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Member> cq = cb.createQuery(Member.class);

                Root<Member> m = cq.from(Member.class);

                cq.select(m)
                    .where(cb.equal(m.get("username"), cb.parameter(String.class, "usernameParam")));

                List<Member> results = em.createQuery(cq)
                    .setParameter("usernameParam", "유저1")
                    .getResultList();

                System.out.println(">> Search result : " + results.size());
            });

            runner.doTask("네이티브 함수", em -> {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Long> cq = cb.createQuery(Long.class);

                Root<Member> m = cq.from(Member.class);
                Expression<Long> function = cb.function("SUM", Long.class, m.get("age"));
                cq.select(function);

                TypedQuery<Long> query = em.createQuery(cq);
                System.out.println("Search result : " + query.getSingleResult());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void set(AbstractDemoRunner runner) {
        try {
            runner.doTask("GROUP BY & HAVING & ORDER", em -> {
                // select team1_.name as col_0_0_, max(member0_.age) as col_1_0_, min(member0_.age) as col_2_0_ from member member0_, team team1_ where member0_.team=team1_.team_id group by team1_.name
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
                Root<Member> m = cq.from(Member.class);

                Expression maxAge = cb.max(m.<Integer>get("age"));
                Expression minAge = cb.min(m.<Integer>get("age"));

                cq.multiselect(m.get("team").get("name"), maxAge, minAge);
                cq.groupBy(m.get("team").get("name"));

                // Orderby : order by m.age desc
                // cq.orderBy(cb.desc(m.get("age")));

                // Having : having min(m.age) > 10
                // cq.having(cb.gt(minAge, 10));

                TypedQuery<Object[]> query = em.createQuery(cq);
                List<Object[]> results = query.getResultList();

                System.out.println(">> Search result : " + results.size());
            });

            runner.doTask("JOIN", em -> {
                // select member0_.member_id as member_i1_0_0_, team1_.team_id as team_id1_3_1_, member0_.age as age2_0_0_, member0_.team as team4_0_0_, member0_.name as name3_0_0_, team1_.name as name2_3_1_ from member member0_ inner join team team1_ on member0_.team=team1_.team_id where team1_.name=?
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

                Root<Member> m = cq.from(Member.class);
                Join<Member, Team> t = m.join("team", JoinType.INNER); // 내부 조인

                cq.multiselect(m, t)
                    .where(cb.equal(t.get("name"), "team01"));

                TypedQuery<Object[]> query = em.createQuery(cq);
                List<Object[]> results = query.getResultList();

                System.out.println(">> Search result : " + results.size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void criteriaQuery(AbstractDemoRunner runner) {
        try {
            runner.doTask("Criteria 쿼리 생성", em -> {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Member> cq = cb.createQuery(Member.class);

                Root<Member> m = cq.from(Member.class);
                // JPQL : select m
                cq.select(m);

                // JPQL : select m.username, m.age
                // cq.multiselect(m.get("username"), m.get("age"));
                // cq.select( cb.array(m.get("username"), m.get("age")) );

                List<Member> members = em.createQuery(cq).getResultList();
                System.out.println(">> Search result : " + members.size());
            });

            runner.doTask("Distinct", em -> {
                // JQPL : select distinct member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
                Root<Member> m = cq.from(Member.class);
                cq.multiselect(m.get("username"), m.get("age")).distinct(true);

                TypedQuery<Object[]> query = em.createQuery(cq);
                List<Object[]> results = query.getResultList();
                System.out.println(">> Search size : " + results.size());
            });

            runner.doTask("NEW, construct()", em -> {
                // select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<MemberDTO> cq = cb.createQuery(MemberDTO.class);
                Root<Member> m = cq.from(Member.class);

                cq.select(cb.construct(MemberDTO.class, m.get("username"), m.get("age")));
                TypedQuery<MemberDTO> query = em.createQuery(cq);

                List<MemberDTO> results = query.getResultList();
                System.out.println(">> Search result : " + results.size());
            });

            runner.doTask("Tuple", em -> {
                // select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                CriteriaBuilder cb = em.getCriteriaBuilder();

                CriteriaQuery<Tuple> cq = cb.createTupleQuery();
                // CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);

                Root<Member> m = cq.from(Member.class);
                // 튜플에서 사용할 튜플 별칭 (SQL의 별칭이 아니라 Tuple 인스턴스의 별칭)
                cq.multiselect(
                    m.get("username").alias("username"),
                    m.get("age").alias("age")
                );

                TypedQuery<Tuple> query = em.createQuery(cq);
                List<Tuple> results = query.getResultList();

                for (Tuple tuple : results) {
                    String username = tuple.get("username", String.class);
                    Integer age = tuple.get("age", Integer.class);
                    System.out.println("username : " + username + ", age : " + age);
                }
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void criteriaUsage(AbstractDemoRunner runner) {
        runner.doTask("Criteria 사용", em -> {
            // 쿼리 빌더
            CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

            // Criteria 생성, 반환 타입 지정
            CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);

            // FROM 절
            Root<Member> memberRoot = criteriaQuery.from(Member.class);

            // 검색 조건 정의
            Predicate usernameEqual = criteriaBuilder.equal(memberRoot.get("username"), "유저1");

            // 정렬 조건 정의
            javax.persistence.criteria.Order ageDesc = criteriaBuilder.desc(memberRoot.get("age"));

            // 쿼리 생성
            criteriaQuery.select(memberRoot)
                .where(usernameEqual)
                .orderBy(ageDesc);

            // SQL : select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.name=? order by member0_.age desc
            TypedQuery<Member> query = em.createQuery(criteriaQuery);
            List<Member> results = query.getResultList();
            System.out.println(">> Search result size " + results.size());
        });
    }

    private static void namedQuery(AbstractDemoRunner runner) {
        runner.doTask("NamedQuery 사용", em -> {
            // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.name=?
            List<Member> results = em.createNamedQuery("Member.findByUsername", Member.class)
                .setParameter("username", "유저1")
                .getResultList();

            System.out.println("Search result : " + results.size());
        });
    }

    private static void useEntityDirectly(AbstractDemoRunner runner) {
        try {
            runner.doTask("엔티티 직접 사용", em -> {
                // select count(member0_.member_id) as col_0_0_ from member member0_
                String jpql = "SELECT count(m) from Member m";
                Long result = (Long) em.createQuery(jpql).getSingleResult();
                System.out.println("result :: " + result);
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void subquery(AbstractDemoRunner runner) {
        try {
            runner.doTask("서브 쿼리 사용", em -> {
                // SQL : select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.age>(select avg(cast(member1_.age as double)) from member member1_)
                String jpql = "SELECT m from Member m where m.age > (SELECT avg(m2.age) FROM Member m2)";
                List<Member> results = em.createQuery(jpql, Member.class).getResultList();
                System.out.println("Search result size : " + results.size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void pathExpression(AbstractDemoRunner runner) {
        try {
            runner.doTask("상태 필드 경로 탐색", em -> {
                // sql : select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                String jpql = "SELECT m.username, m.age from Member m";
                List<Object[]> results = em.createQuery(jpql).getResultList();
                for (Object[] result : results) {
                    System.out.println(Arrays.toString(result));
                }
            });

            runner.doTask("단일 값 연관 경로 탐색", em -> {
                // SQL : select member1_.member_id as member_i1_0_, member1_.age as age2_0_, member1_.team as team4_0_, member1_.name as name3_0_ from orders order0_ inner join member member1_ on order0_.member_id=member1_.member_id
                String jqpl = "SELECT o.member from Order o";
                List<Object[]> results = em.createQuery(jqpl).getResultList();
                System.out.println("Search result size : " + results.size());
            });

            runner.doTask("컬렉션 값 연관 경로 탐색", em -> {
                String jpql = "SELECT t.members from Team t";
                List<Object[]> results = em.createQuery(jpql).getResultList();
                System.out.println("Search result size : " + results.size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void jpqlJoin(AbstractDemoRunner runner) {
        try {
            runner.doTask("내부 조인", em -> {
                String teamName = "team01";
                // Team 이라 하지 않고, m.team 으로 조인
                // 외부 조인은 Outer로
                String query = "SELECT m FROM Member m INNER JOIN m.team t "
                    + "WHERE t.name = :teamName";
                List<Member> results = em.createQuery(query, Member.class)
                    .setParameter("teamName", teamName)
                    .getResultList();

                for (Member member : results) {
                    System.out.printf(
                        "Username : %s(%d) in %s\n", member.getUsername(), member.getAge(), member.getTeam().getName()
                    );
                }
            });

            runner.doTask("컬렉션 조인", em -> {
                List<Object[]> teams = em.createQuery("SELECT t, m FROM Team t LEFT JOIN t.members m")
                    .getResultList();

                for (Object[] team : teams) {
                    System.out.println(Arrays.toString(team));
                }
            });

            runner.doTask("세타조인", em -> {
                // SQL : select count(member0_.member_id) as col_0_0_ from member member0_ cross join team team1_ where member0_.name=team1_.name
                Object result = em.createQuery("SELECT COUNT(m) from Member m, Team t where m.username = t.name")
                    .getSingleResult();

                System.out.println("Count :: " + result);
            });

            runner.doTask("JOIN ON", em -> {
                // SQL : select member0_.member_id as member_i1_0_0_, team1_.team_id as team_id1_3_1_, member0_.age as age2_0_0_, member0_.team as team4_0_0_, member0_.name as name3_0_0_, team1_.name as name2_3_1_ from member member0_ left outer join team team1_ on member0_.team=team1_.team_id and (team1_.name='team01')
                List<Object[]> results
                    = em.createQuery("SELECT m,t from Member m left join m.team t on t.name = 'team01'")
                    .getResultList();

                for (Object[] result : results) {
                    Member member = (Member) result[0];
                    Team team = (Team) result[1];
                    System.out.println("Member.username : " + member.getUsername()
                        + "Team.name : " + (team == null ? "null" : team.getName()));
                }
            });

            runner.doTask("페치 조인", em -> {
                // SQL : select member0_.member_id as member_i1_0_0_, team1_.team_id as team_id1_3_1_, member0_.age as age2_0_0_, member0_.team as team4_0_0_, member0_.name as name3_0_0_, team1_.name as name2_3_1_ from member member0_ inner join team team1_ on member0_.team=team1_.team_id
                String jpql = "SELECT m from Member m join fetch m.team";
                List<Member> results = em.createQuery(jpql, Member.class)
                    .getResultList();

                for (Member member : results) {
                    System.out.println(
                        "username : " + member.getUsername() + ", team name : " + member.getTeam().getName()
                    );
                }
            });

            runner.doTask("컬렉션 페치 조인", em -> {
                // SQL : select team0_.team_id as team_id1_3_0_, members1_.member_id as member_i1_0_1_, team0_.name as name2_3_0_, members1_.age as age2_0_1_, members1_.team as team4_0_1_, members1_.name as name3_0_1_, members1_.team as team4_3_0__, members1_.member_id as member_i1_0_0__ from team team0_ inner join member members1_ on team0_.team_id=members1_.team where team0_.name='team01'
                // SELECT distinct t 하면 distinct 추가는 됨 + 애플리케이션에서 중복된 데이터를 걸러냄
                String jpql = "SELECT t from Team t join fetch t.members where t.name = 'team01'";
                // String jpql = "SELECT distinct t from Team t join fetch t.members where t.name = 'team01'";
                List<Team> teams = em.createQuery(jpql, Team.class)
                    .getResultList();
                // ==> team01 n 개 나옴
                System.out.println(">> search result size : " + teams.size());
                for (Team team : teams) {
                    System.out.println("Team name : " + team.getName() + " with members : " + team.getMembers().size());
                }
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void reportingQuery(AbstractDemoRunner runner) {
        try {
            runner.doTask("GROUP BY", em -> {
                // select team1_.name as col_0_0_, count(member0_.age) as col_1_0_, sum(member0_.age) as col_2_0_, max(member0_.age) as col_3_0_, min(member0_.age) as col_4_0_ from member member0_ left outer join team team1_ on member0_.team=team1_.team_id group by team1_.name
                List<Object[]> results =
                    em.createQuery("SELECT t.name, COUNT(m.age), SUM(m.age), MAX(m.age), MIN(m.age)"
                        + " from Member m LEFT JOIN m.team t GROUP BY t.name")
                        .getResultList();

                for (Object[] result : results) {
                    System.out.println(Arrays.toString(result));
                }
//              [team01, 1, 1, 1, 1]
//              [team02, 1, 4, 4, 4]
//              [team03, 1, 5, 5, 5]
            });

            runner.doTask("Order by", em -> {
                // select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ order by member0_.age DESC, member0_.name ASC
                List<Member> results =
                    em.createQuery("SELECT m from Member m order by m.age DESC, m.username ASC", Member.class)
                        .getResultList();

                for (Member member : results) {
                    System.out.printf("Member username : %s, age : %d\n", member.getUsername(), member.getAge());
                }
//                Member username : 유저4, age : 4
//                Member username : 유저2, age : 2
//                Member username : 유저1, age : 1
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void setAndSort(AbstractDemoRunner runner) {
        try {
            runner.doTask("집합 함수", em -> {
                // select count(member0_.member_id) as col_0_0_, sum(member0_.age) as col_1_0_, avg(cast(member0_.age as double)) as col_2_0_, max(member0_.age) as col_3_0_, min(member0_.age) as col_4_0_ from member member0_
                List<Object[]> aggregate = em
                    .createQuery("SELECT COUNT(m), SUM(m.age), AVG(m.age), MAX(m.age), MIN(m.age) from Member m")
                    .getResultList();

                for (Object[] result : aggregate) {
                    System.out.println(Arrays.toString(result));
                }

//                Output
//                [5, 15, 3.0, 5, 1]
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void pagingAPI(AbstractDemoRunner runner) {
        try {
            runner.doTask("페이징 사용", em -> {
                // Database Dialect에 따른 쿼리 생성
                TypedQuery<Member> query = em.createQuery("SELECT m from Member m ORDER BY m.username DESC"
                    , Member.class);

                query.setFirstResult(2);   // 조회 시작 위치(0부터시작)
                query.setMaxResults(20);    // 조회 할 데이터 수

                System.out.println("Search size : " + query.getResultList().size());
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void projection(AbstractDemoRunner runner) {
        try {
            runner.doTask("임베디드 타입 프로젝션", em -> {
                // SQL : select order0_.city as col_0_0_, order0_.street as col_0_1_, order0_.zipcode as col_0_2_ from orders order0_
                String query = "SELECT o.address FROM Order o";
                List<Address> addresses = em.createQuery(query, Address.class)
                    .getResultList();
                System.out.println("Address search result size : " + addresses.size());
            });

            runner.doTask("스칼라 타입 프로젝션", em -> {
                // SQL : select distinct member0_.name as col_0_0_ from member member0_
                List<String> usernames = em.createQuery("SELECT DISTINCT username FROM Member m")
                    .getResultList();

                System.out.println("Usernames size : " + usernames.size());
            });

            runner.doTask("여러 프로젝션 Object[] 조회", em -> {
                // SQL : select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                List<Object[]> results = em.createQuery("SELECT m.username, m.age FROM Member m")
                    .getResultList();

                for (Object[] row : results) {
                    String username = (String) row[0];
                    Integer age = (Integer) row[1];
                    System.out.printf("username : %s (%d)\n", username, age);
                }
            });

            runner.doTask("NEW 명령어", em -> {
                // SQL : select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
                TypedQuery<MemberDTO> query = em.createQuery("SELECT new jpabook.dto.UserDTO(m.username, m.age)"
                    + "FROM Member m", MemberDTO.class);
                System.out.println("DTO size :: " + query.getResultList().size());
            });


        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void parameterBinding(AbstractDemoRunner runner) {
        try {
            runner.doTask("이름 기준 파라미터", em -> {
                String usernameParam = "유저1";
                TypedQuery<Member> query
                    = em.createQuery("SELECT m FROM Member m where m.username = :username", Member.class);

                query.setParameter("username", usernameParam);

                List<Member> results = query.getResultList();
                for (Member member : results) {
                    System.out.println("Member.username : " + member.getUsername());
                }
            });

            runner.doTask("위치 기준 파라미터", em -> {
                String usernameParam = "유저1";
                List<Member> results = em.createQuery("SELECT m FROM Member m where m.username = ?1", Member.class)
                    .setParameter(1, usernameParam)
                    .getResultList();

                for (Member member : results) {
                    System.out.println("Member.username : " + member.getUsername());
                }
            });

//            ---------------------------- 이름 기준 파라미터----------------------------
//            Hibernate: /* SELECT m FROM Member m where m.username = :username */ select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.name=?
//            Member.username : 유저1
//            ---------------------------------------------------------------------------
//            ---------------------------- 위치 기준 파라미터----------------------------
//            Hibernate: /* SELECT m FROM Member m where m.username = ?1 */ select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_ where member0_.name=?
//            Member.username : 유저1
//            ---------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private static void defaultApi(AbstractDemoRunner runner) {
        try {
            // TypeQuery 사용
            runner.doTask("TypedQuery 사용", em -> {
                TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
                List<Member> result = query.getResultList();
                for (Member member : result) {
                    System.out.printf("Member id : %d / name : %s\n", member.getId(), member.getUsername());
                }
            });

            runner.doTask("Query 사용", em -> {
                Query query = em.createQuery("SELECT m.username, m.age from Member m");
                List resultList = query.getResultList();
                for (Object o : resultList) {
                    Object[] result = (Object[]) o; // 결과가 둘 이상이면 Object[] 반환
                    System.out.printf("username = %s, age = %s\n", result[0], result[1]);
                }
            });

//            ---------------------------- TypedQuery 사용----------------------------
//            Hibernate: /* SELECT m FROM Member m */ select member0_.member_id as member_i1_0_, member0_.age as age2_0_, member0_.team as team4_0_, member0_.name as name3_0_ from member member0_
//            Member id : 1 / name : 유저1
//            Member id : 2 / name : 유저2
//            Member id : 3 / name : 유저3
//            Member id : 4 / name : 유저4
//            Member id : 5 / name : 유저5
//                ---------------------------------------------------------------------------
//                ---------------------------- Query 사용----------------------------
//            Hibernate: /* SELECT m.username, m.age from Member m */ select member0_.name as col_0_0_, member0_.age as col_1_0_ from member member0_
//                username = 유저1, age = 1
//            username = 유저2, age = 2
//            username = 유저3, age = 3
//            username = 유저4, age = 4
//            username = 유저5, age = 5
//                ---------------------------------------------------------------------------
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}
