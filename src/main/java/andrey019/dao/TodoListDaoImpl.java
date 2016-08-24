package andrey019.dao;

import andrey019.model.dao.TodoList;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository("todoListDao")
public class TodoListDaoImpl implements TodoListDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public boolean save(TodoList todoList) {
        try {
            entityManager.merge(todoList);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(TodoList todoList) {
        try {
            todoList = entityManager.merge(todoList);
            entityManager.remove(todoList);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Transactional
    @Override
    public TodoList getById(long id) {
        try {
            return entityManager.find(TodoList.class, id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
