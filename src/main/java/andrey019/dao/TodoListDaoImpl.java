package andrey019.dao;

import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
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
    public TodoList merge(TodoList todoList) {
        return entityManager.merge(todoList);
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

    @Transactional
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

    @Override
    public TodoList getByIdWithUsersAndSharedLists(long id) {
        @SuppressWarnings("unchecked")
        List<TodoList> result = entityManager.createQuery("select list from TodoList list " +
                "left join fetch list.users as user left join fetch user.sharedTodoLists where list.id = :idParam")
                .setParameter("idParam", id).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }
}
