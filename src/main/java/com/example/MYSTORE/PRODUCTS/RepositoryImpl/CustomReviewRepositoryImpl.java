package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Repository.CustomReviewRepository;
import com.example.MYSTORE.SECURITY.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Reviews> getReviewsByTeaId(Long id) {
        List<Reviews> reviews = em.createQuery("select r from Reviews r join r.teas t where t.id =?1",Reviews.class)
                .setParameter(1,id)
                .getResultList().stream().toList();
        return reviews;
    }

    @Override
    @Transactional
    public Long countReviewsByTea(Tea tea) {
        Long count = em.createQuery("select count(*) from Reviews r left join r.tea rt where rt.id = ?1",Long.class)
                .setParameter(1,tea.getId())
                .getSingleResult();
        return count;
    }

    @Override
    @Transactional
    public void saveNewReview(Reviews reviews) {
        em.persist(reviews);
    }

    @Override
    @Transactional
    public void updateReviewAndTea(Reviews reviews, Tea tea) {
        em.createNativeQuery("insert into tea_and_review (tea_id,review_id) values(?1,?2)")
                .setParameter(1,tea.getId())
                .setParameter(2,reviews.getId())
                .executeUpdate();
    }

    @Override
    @Transactional
    public Reviews getReviewById(Long id) {
        Reviews reviews = em.createQuery("select r from Reviews r where r.id = ?1",Reviews.class)
                .setParameter(1,id)
                .getResultList().stream().findFirst().orElse(null);
        return reviews;
    }

    @Override
    @Transactional
    public void updateReviewAndUser(Reviews reviews, User user) {
        em.createNativeQuery("insert into user_and_review (user_id,review_id) values(?1,?2)")
                .setParameter(1, user.getId())
                .setParameter(2,reviews.getId())
                .executeUpdate();
    }
    @Override
    @Transactional
    public void updateReviewAndUser(Long rid, Long uid) {
        em.createNativeQuery("insert into user_and_review (user_id,review_id) values(?1,?2)")
                .setParameter(1,uid)
                .setParameter(2,rid)
                .executeUpdate();
    }

    @Override
    @Transactional
    public void uploadReview(Reviews reviews) {

    }
}
