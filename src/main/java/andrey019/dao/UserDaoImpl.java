package andrey019.dao;

import andrey019.model.dao.User;
import org.dellroad.stuff.spring.RetryTransaction;
import org.dellroad.stuff.spring.RetryTransactionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

//    @Autowired
//    private RetryTransactionProvider retryTransactionProvider;

    @PersistenceContext
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

//    @RetryTransaction

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public User getByEmailWithSharedLists(String email) {
        try {
            @SuppressWarnings("unchecked")
            List<User> result = entityManager.createQuery("select user from User user left join fetch user.sharedTodoLists " +
                    "where user.email = :emailParam")
                    .setParameter("emailParam", email).getResultList();
            if (result.isEmpty()) {
                return null;
            }
            return result.get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
