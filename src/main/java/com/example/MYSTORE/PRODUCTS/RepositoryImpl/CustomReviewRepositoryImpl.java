package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Repository.CustomReviewRepository;
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
    public void saveNewReview(Reviews reviews) {
        em.persist(reviews);
    }

    @Override
    @Transactional
    public void updateReviewAndTea(Reviews reviews, Tea tea) {
        em.createNativeQuery("insert into tea_and_review (tea_id,review_id) values(?1,?2)")
                .setParameter(1,tea.getId())
                .setParameter(2,reviews.getId());
    }
}
