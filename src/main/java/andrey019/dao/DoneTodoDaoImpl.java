package andrey019.dao;


import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("doneTodoDao")
public class DoneTodoDaoImpl implements DoneTodoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean save(DoneTodo doneTodo) {
        try {
            entityManager.merge(doneTodo);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(DoneTodo doneTodo) {
        try {
            doneTodo = entityManager.merge(doneTodo);
            entityManager.remove(doneTodo);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public List<DoneTodo> getByCreatedEmail(String email) {
        @SuppressWarnings("unchecked")
        List<DoneTodo> result = entityManager.createQuery("select doneTodo from DoneTodo doneTodo where doneTodo.createdByEmail = :emailParam")
                .setParameter("emailParam", email).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }
}
