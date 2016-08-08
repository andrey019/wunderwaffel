package andrey019.dao;


import andrey019.model.dao.Todo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.List;

@Repository("todoDaoImpl")
public class TodoDaoImpl implements TodoDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public Todo getById(long id) {
        try {
            return entityManager.find(Todo.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Transactional
    @Override
    public boolean save(Todo todo) {
        try {
            entityManager.merge(todo);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(Todo todo) {
        try {
            todo = entityManager.merge(todo);
            entityManager.remove(todo);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public List<Todo> getByCreatedEmail(String email) {
        @SuppressWarnings("unchecked")
        List<Todo> result = entityManager.createQuery("select todo from Todo todo where todo.createdByEmail = :emailParam")
                .setParameter("emailParam", email).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }
}
