package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Repository.CustomReviewRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomReviewRepositoryImpl implements CustomReviewRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Reviews> getReviewsByTeaId(Long id) {
        Set<Reviews> reviews = em.createQuery("select r from Reviews r join r.teas t where t.id =?1",Reviews.class)
                .setParameter(1,id).getResultList().stream().collect(Collectors.toSet());
        return reviews;
    }
}
