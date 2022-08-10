package com.example.MYSTORE.SECURITY.RepositoryImpl;

import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.CustomJWTRefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomJWTRTokenRepositoryImpl implements CustomJWTRefreshTokenRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void saveNewJWTRToken(JWTRefreshToken jwtRefreshToken) {
        em.persist(jwtRefreshToken);
    }

    @Override
    @Transactional
    public void deleteJWTRToken(JWTRefreshToken jwtRefreshToken) {
        em.remove(em.contains(jwtRefreshToken) ? jwtRefreshToken : em.merge(jwtRefreshToken));
    }

    @Override
    @Transactional
    public void updateJWTRTokenAndUser(JWTRefreshToken jwtRefreshToken, User user) {
        em.createNativeQuery("insert into user_and_jwt (user_id,jwt_id) values(?1,?2)")
                .setParameter(1,user.getId())
                .setParameter(2,jwtRefreshToken.getId())
                .executeUpdate();
    }

    @Override
    @Transactional
    public JWTRefreshToken getJWTRTokenByRefreshToken(String token) {
        JWTRefreshToken jwtRefreshToken = em.createQuery("select jwt from JWTRefreshToken jwt " +
                "wher jwt.refreshToken = ?1",JWTRefreshToken.class)
                .setParameter(1,token)
                .getResultList().stream().findFirst().orElse(null);
        return jwtRefreshToken;
    }
}
