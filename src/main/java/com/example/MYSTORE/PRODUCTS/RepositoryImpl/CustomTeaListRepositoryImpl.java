package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.TeaLists;
import com.example.MYSTORE.PRODUCTS.Repository.CustomTeaListRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomTeaListRepositoryImpl implements CustomTeaListRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void saveNewTeaList(TeaLists teaLists) {
        em.persist(teaLists);
    }

    @Override
    @Transactional
    public void deleteTeaList(TeaLists teaLists) {
        em.remove(em.contains(teaLists) ? teaLists : em.merge(teaLists));
    }

    @Override
    @Transactional
    public TeaLists getTeaListByName(String name) {
        TeaLists teaLists = em.createQuery("select tl from TeaLists tl " +
                        "left join fetch tl.teas1 where tl.name = ?1",TeaLists.class)
                .setParameter(1,name)
                .getResultList().stream().findFirst().orElse(null);
        return teaLists;
    }

    @Override
    @Transactional
    public void uploadTeaListAndTeaById(Long tid,Long tlid) {
        em.createNativeQuery("insert into tea_and_list (tea_id,list_id) values(?1,?2)")
                .setParameter(1,tid)
                .setParameter(2,tlid)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void deleteRelationTeaListAndTeaByName(Long tlid,Long tid) {
        em.createNativeQuery("delete from tea_and_list as tl where tl.list_id = ?1 and tl.tea_id = ?2")
                .setParameter(1,tlid)
                .setParameter(2,tid)
                .executeUpdate();
    }
}
