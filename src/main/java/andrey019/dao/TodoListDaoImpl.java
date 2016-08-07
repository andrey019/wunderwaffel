package andrey019.dao;


import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository("todoListDao")
public class TodoListDaoImpl implements TodoListDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(isolation = Isolation.SERIALIZABLE)
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
        return entityManager.find(TodoList.class, id);
    }

    @Transactional
    @Override
    public TodoList getByIdWithTodos(long id) {
        @SuppressWarnings("unchecked")
        List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                "left join fetch list.todos where list.id = :idParam")
                .setParameter("idParam", id).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Transactional
    @Override
    public TodoList getByIdWithDoneTodos(long id) {
        @SuppressWarnings("unchecked")
        List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                "left join fetch list.doneTodos where list.id = :idParam")
                .setParameter("idParam", id).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public TodoList getByIdWithTodosAndDoneTodos(long id) {
        @SuppressWarnings("unchecked")
        List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                "left join fetch list.todos left join fetch list.doneTodos where list.id = :idParam")
                .setParameter("idParam", id).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Transactional
    @Override
    public List<TodoList> getByUserId(long id) {
        @SuppressWarnings("unchecked")
        List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                "where list.user = :userIdParam")
                .setParameter("userIdParam", id).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Transactional
    @Override
    public List<TodoList> getByUsers(long id) {
        @SuppressWarnings("unchecked")
        List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                "inner join list.users user where user.id = :userId")
                .setParameter("userId", id).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }

    @Transactional
    @Override
    public List<User> getUsersByTodoListId(long id) {
        @SuppressWarnings("unchecked")
        List<User> result = entityManager.createQuery("select users from User users " +
                "inner join users.sharedTodoLists list where list.id = :listId")
                .setParameter("listId", id).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result;
    }
}
