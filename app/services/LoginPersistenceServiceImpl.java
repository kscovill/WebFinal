package services;

import jpa.Login;

import java.util.List;

import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
public class LoginPersistenceServiceImpl implements LoginPersistenceService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void saveLogin(Login login) {
        em.persist(login);
    }

    @Override
    public List<Login> fetchAllUsers() {
        return em.createQuery("from Login ", Login.class).getResultList();
    }
}
