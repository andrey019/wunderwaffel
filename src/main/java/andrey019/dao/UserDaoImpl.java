package andrey019.dao;

import andrey019.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findBySSO(String sso) {
        return (User) entityManager.createQuery(
                "select c from User c where c.ssoId = :ssoIdParam")
                .setParameter("ssoIdParam", sso)
                .getSingleResult();
    }
}
