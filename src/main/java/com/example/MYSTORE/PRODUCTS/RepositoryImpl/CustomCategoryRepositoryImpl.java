package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Repository.CustomCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomCategoryRepositoryImpl implements CustomCategoryRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Category getCategoryById(Long id) {
        Category category = em.createQuery("select c from Category c where c.id = ?1",Category.class)
                .setParameter(1,id)
                .getResultList().stream().findFirst().orElse(null);
        return category;
    }

    @Override
    @Transactional
    public Category getCategoryByName(String name) {
        Category category = em.createQuery("select c from Category c where c.name = ?1",Category.class)
                .setParameter(1,name)
                .getResultList().stream().findFirst().orElse(null);
        return category;
    }

    @Override
    @Transactional
    public void updateCategory(Category category) {
        em.merge(category);
    }

    @Override
    @Transactional
    public void updateCategoryAndTea(Category category, Tea tea) {
        em.createNativeQuery("insert into tea_and_category (tea_id,category_id) values(?1,?2)")
                .setParameter(1,tea.getId())
                .setParameter(2,category.getId())
                .executeUpdate();
    }

    @Override
    @Transactional
    public void saveNewCategory(Category category) {
        em.persist(category);
    }
}
