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
            String query = "select" +
                                "case when m.age <=10 then '학생요금   " +
                                "     when m.age >=60 then '성인요금   " +
                                "     else '일반 요금' end   ";
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
