package jpabook;

import java.util.List;
import jpabook.entity.Board;
import jpabook.entity.Comment;
import jpabook.entity.Member;
import jpabook.entity.Team;

/**
 * https://github.com/holyeye/jpabook
 */
public class CollectionDemo {

    public static void main(String[] args) {
        AbstractDemoRunner runner = new AbstractDemoRunner();
        try {
            runner.doTask("Collection class", em -> {
                Team team = new Team();
                team.setName("team01");
                System.out.println("Before persist : " + team.getMembers().getClass());
                em.persist(team);
                System.out.println("After persist : " + team.getMembers().getClass());

//                Output :
//                Before persist : class java.util.ArrayList
//                After persist : class org.hibernate.collection.internal.PersistentBag
            });

            runner.doTask("@OrderColumn test", em -> {
                Board board = new Board();
                board.setTitle("제목1");
                board.setContent("내용1");

                em.persist(board);

                Comment comment1 = new Comment();
                comment1.setComment("댓글1");
                comment1.setBoard(board);
                board.getComments().add(comment1);  // POSITION 0
                em.persist(comment1);

                Comment comment2 = new Comment();
                comment2.setComment("댓글2");
                comment2.setBoard(board);
                board.getComments().add(comment2);  // POSITION 1
                em.persist(comment2);
            });

            runner.doTask("converter", em -> {
                Member member1 = new Member();
                member1.setUsername("member01");
                member1.setVip(true);

                em.persist(member1);

                Member member2 = new Member();
                member2.setUsername("member02");
                member2.setVip(false);

                em.persist(member2);

                List<Member> members = em.createQuery("SELECT m FROM Member m", Member.class)
                    .getResultList();

                for (Member member : members) {
                    System.out.println(member.getUsername() + " :: " + member.isVip());
                }
            });
        } catch (Exception e) {
            e.printStackTrace(System.err);
        } finally {
            runner.close();
        }
    }

}
