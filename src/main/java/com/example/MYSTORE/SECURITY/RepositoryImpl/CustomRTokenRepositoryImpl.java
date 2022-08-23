package com.example.MYSTORE.SECURITY.RepositoryImpl;

import com.example.MYSTORE.SECURITY.Model.ResetPasswordToken;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Model.VerificationToken;
import com.example.MYSTORE.SECURITY.Repository.CustomRefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomRTokenRepositoryImpl implements CustomRefreshTokenRepository {
    @PersistenceContext
    private EntityManager em;
    @Override
    @Transactional
    public void saveNewRToken(ResetPasswordToken resetPasswordToken) {
        em.persist(resetPasswordToken);
    }

    @Override
    @Transactional
    public void deleteRToken(ResetPasswordToken resetPasswordToken) {
        em.remove(em.contains(resetPasswordToken) ? resetPasswordToken : em.merge(resetPasswordToken));
    }

    @Override
    @Transactional
    public ResetPasswordToken getRTokenByUser(User user) {
        ResetPasswordToken resetPasswordToken = em.createQuery("select rt from ResetPasswordToken rt " +
                        "left join rt.user rtu where rtu.id = ?1",ResetPasswordToken.class)
                .setParameter(1,user.getId())
                .getResultList().stream().findFirst().orElse(null);
        return resetPasswordToken;
    }

    @Override
    @Transactional
    public void updateRTokenAndUser(ResetPasswordToken resetPasswordToken, User user) {
        em.createNativeQuery("insert into user_and_rtoken (user_id,token_id) values(?1,?2)")
                .setParameter(1,user.getId())
                .setParameter(2,resetPasswordToken.getId())
                .executeUpdate();
    }
}
