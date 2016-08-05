package andrey019.dao;

import andrey019.model.dao.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    @Transactional
    @Override
    public User getById(long id) {
        return entityManager.find(User.class, id);
    }

    @Transactional
    @Override
    public boolean save(User user) {
        try {
            entityManager.merge(user);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public User getByEmail(String email) {
        @SuppressWarnings("unchecked")
        List<User> result = entityManager.createQuery("select user from User user where user.email = :emailParam")
                .setParameter("emailParam", email).setMaxResults(1).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
