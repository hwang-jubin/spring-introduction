package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
//gradle file에서 data-jdbc 라이브러리를 받으면 boot가 database와 연동해서 EntityManger자동으로 생성
    //이러면 injection 받기만 하면 됨

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); //em을 영구적으로 저장(persist)
        return member;
    }
//이렇게 하면 insert쿼리 넣어서 다 집어넣고 member에 setId까지 해줌


    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);//조회하기
        return Optional.ofNullable(member);//없을 수도 있으니까 이렇게 감싸주기
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)//Member as m
                .getResultList(); //Member 객체 자체(entity)를 상대로 query 를 날림. 객체 자체를 select
    }

}
