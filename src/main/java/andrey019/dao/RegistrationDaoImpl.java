package andrey019.dao;


import andrey019.model.UserConfirmation;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("registrationDao")
public class RegistrationDaoImpl implements RegistrationDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean save(UserConfirmation userConfirmation) {
        //entityManager.getTransaction().begin();
        try {
            entityManager.persist(userConfirmation);
            //entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            //entityManager.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public UserConfirmation getByEmail(String email) {
        @SuppressWarnings("unchecked")
        List<UserConfirmation> resultList = entityManager
                .createQuery("select c from UserConfirmation c where c.email = :email")
                .setParameter("email", email).setMaxResults(1).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public UserConfirmation getByCode(String code) {
        @SuppressWarnings("unchecked")
        List<UserConfirmation> resultList = entityManager
                .createQuery("select c from UserConfirmation c where c.code = :code")
                .setParameter("code", code).setMaxResults(1).getResultList();
        if (resultList.isEmpty()) {
            return null;
        }
        return resultList.get(0);
    }

    @Override
    public List<UserConfirmation> getByDateOlderThen(long date) {
        @SuppressWarnings("unchecked")
        List<UserConfirmation> resultList = entityManager
                .createQuery("select c from UserConfirmation c where c.date < :date")
                .setParameter("date", date).getResultList();
        return resultList;
    }

    @Override
    public boolean delete(UserConfirmation userConfirmation) {
        entityManager.getTransaction().begin();
        try {
            entityManager.remove(userConfirmation);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public void deleteList(List<UserConfirmation> list) {
        entityManager.getTransaction().begin();
        try {
            for (UserConfirmation user : list) {
                entityManager.remove(user);
            }
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
