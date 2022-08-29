package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.SlaiderImages;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import com.example.MYSTORE.PRODUCTS.Repository.CustomSlaiderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomSlaiderRepositoryImpl implements CustomSlaiderRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void saveNewSlaider(SlaiderImages slaiderImages) {
        em.persist(slaiderImages);
    }

    @Override
    @Transactional
    public void deleteSlaider(SlaiderImages slaiderImages) {
        em.remove(em.contains(slaiderImages) ? slaiderImages : em.merge(slaiderImages));
    }

    @Override
    @Transactional
    public SlaiderImages getSlaiderAndTeaImageByName(String name) {
        SlaiderImages slaiderImages = em.createQuery("select s from SlaiderImages s " +
                        "left join fetch s.teaImages st where s.name = ?1",SlaiderImages.class)
                .setParameter(1,name)
                .getResultList().stream().findFirst().orElse(null);
        return slaiderImages;
    }

    @Override
    @Transactional
    public void updateSlaiderAndTeaImage(SlaiderImages slaiderImages, TeaImage teaImage) {
        em.createNativeQuery("insert into slaider_and_image (image_id,slaider_id) values(?1,?2)")
                .setParameter(1,teaImage.getId())
                .setParameter(2,slaiderImages.getId())
                .executeUpdate();
    }

}
