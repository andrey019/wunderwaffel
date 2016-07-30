package andrey019.dao;

import andrey019.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean save(User user) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    @Override
    public User findByEmail(String email) {
        @SuppressWarnings("unchecked")
        List<User> result = entityManager.createQuery("select c from User c where c.email = :emailParam")
                .setParameter("emailParam", email).setMaxResults(1).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
