package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import com.example.MYSTORE.PRODUCTS.Repository.CustomTeaImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomTeaImageRepositoryImpl implements CustomTeaImageRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void saveNewTeaImage(TeaImage teaImage) {
        em.persist(teaImage);
    }

    @Override
    public void updateTeaImageAndTea(TeaImage teaImage, Tea tea) {
        em.createNativeQuery("insert into tea_and_image (tea_id,image_id) values(?1,?2)")
                .setParameter(1,tea.getId())
                .setParameter(2,teaImage.getId());
    }
}
