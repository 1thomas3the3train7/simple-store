package com.example.MYSTORE.PRODUCTS.Repository;

import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomTeaRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Integer findMaxPrice(){
        return (Integer) entityManager.createQuery("select max(price) from Tea").getSingleResult();
    }
    public Integer findMinPrice(){
        return (Integer) entityManager.createQuery("select min(price) from Tea").getSingleResult();
    }
}
