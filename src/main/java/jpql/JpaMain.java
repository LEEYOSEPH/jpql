package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

                Team team = new Team();
                team.setName("TeamA");
                em.persist(team);


                Member member = new Member();
                member.setUsername("member1");
                member.setAge(10);
                member.setTeam(team);
                em.persist(member);



                em.flush();
                em.clear();
            String query = "select m.username, 'hello', true from Member m inner join m.team t" +
                    "where m.type = jpql.MemberType.USER " ;
            List<Object> result = em.createQuery(query)
                                            .getResultList();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
