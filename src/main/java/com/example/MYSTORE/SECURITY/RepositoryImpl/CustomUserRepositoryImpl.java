package com.example.MYSTORE.SECURITY.RepositoryImpl;

import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.CustomUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class CustomUserRepositoryImpl implements CustomUserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public User getUserByEmail(String email) {
        User user = em.createQuery("select u from User u where u.email=?1",User.class)
                .setParameter(1,email)
                .getResultList().stream().findFirst().orElse(null);
        return user;
    }

    @Override
    @Transactional
    public void saveNewUser(User user) {
        em.persist(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        em.remove(em.contains(user) ? user : em.merge(user));
    }

    @Override
    @Transactional
    public User getUserAndVTokenByEmail(String email) {
        User user = em.createQuery("select u from User u left join fetch u.verificationToken uv where u.email =?1",User.class)
                .setParameter(1,email)
                .getResultList().stream().findFirst().orElse(null);
        return user;
    }

    @Override
    @Transactional
    public User getUserByJWTRefreshToken(JWTRefreshToken jwtRefreshToken) {
        User user = em.createQuery("select u from User u " +
                        "left join u.jwtRefreshToken uj where uj.id = ?1",User.class)
                .setParameter(1,jwtRefreshToken.getId())
                .getResultList().stream().findFirst().orElse(null);
        return user;
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        em.createQuery("update User u set u.email = ?1, u.password = ?2, u.username = ?3, u.myenabled = ?4 where u.id = ?5")
                .setParameter(1,user.getEmail())
                .setParameter(2,user.getPassword())
                .setParameter(3,user.getUsername())
                .setParameter(4,user.getMyEnabled())
                .setParameter(5,user.getId())
                .executeUpdate();
    }
    @Override
    @Transactional
    public User getUserByTeaIdAndEmail(Long id, String email) {
        User user = em.createQuery("select u from User u " +
                        "left join u.teas ut where ut.id = ?1 and u.email = ?2",User.class)
                .setParameter(1,id)
                .setParameter(2,email)
                .getResultList().stream().findFirst().orElse(null);
        return user;
    }
}
