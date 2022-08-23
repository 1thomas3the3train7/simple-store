package com.example.MYSTORE.PRODUCTS.RepositoryImpl;

import com.example.MYSTORE.PRODUCTS.Model.SlaiderImages;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import com.example.MYSTORE.PRODUCTS.Repository.CustomTeaImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    @Transactional
    public void updateTeaImageAndTea(TeaImage teaImage, Tea tea) {
        em.createNativeQuery("insert into tea_and_image (tea_id,image_id) values(?1,?2)")
                .setParameter(1,tea.getId())
                .setParameter(2,teaImage.getId());
    }

    @Override
    @Transactional
    public TeaImage getTeaImageByName(String name) {
        TeaImage teaImage = em.createQuery("select t from TeaImage t where t.linkImage = ?1",TeaImage.class)
                .setParameter(1,name)
                .getResultList().stream().findFirst().orElse(null);
        return teaImage;
    }

    @Override
    @Transactional
    public void deleteTeaImageBySlaider(SlaiderImages slaiderImages) {
        SlaiderImages slaiderImages1 = em.createQuery("select s from SlaiderImages s " +
                "left join fetch s.teaImages where s.id = ?1",SlaiderImages.class)
                .setParameter(1,slaiderImages.getId())
                .getResultList().stream().findFirst().orElse(null);
        if(slaiderImages1 != null){
            for(TeaImage t : slaiderImages1.getTeaImages()){
                em.remove(t);
            }
        }
    }
}
