package com.example.MYSTORE.SECURITY.RepositoryImpl;

import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Model.VerificationToken;
import com.example.MYSTORE.SECURITY.Repository.CustomVerificationTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Service
public class CustomVTokenRepositoryImpl implements CustomVerificationTokenRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void saveNewVToken(VerificationToken verificationToken) {
        em.persist(verificationToken);
    }

    @Override
    @Transactional
    public VerificationToken getVTokenByUser(User user) {
        VerificationToken verificationToken =
                em.createQuery("select vt from VerificationToken vt left" +
                                " join vt.user vtu where vtu.id = ?1",VerificationToken.class)
                        .setParameter(1,user.getId())
                        .getResultList().stream().findFirst().orElse(null);
        return verificationToken;
    }

    @Override
    @Transactional
    public void deleteVToken(VerificationToken verificationToken) {
        em.remove(em.contains(verificationToken) ? verificationToken : em.merge(verificationToken));
    }

    @Override
    @Transactional
    public void updateVTokenAndUser(VerificationToken verificationToken, User user) {
        em.createNativeQuery("insert into user_and_vtoken (user_id,token_id) values(?1,?2)")
                .setParameter(1,user.getId())
                .setParameter(2,verificationToken.getId())
                .executeUpdate();
    }
}
