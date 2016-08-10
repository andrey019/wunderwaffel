package andrey019.dao;


import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;

import java.util.List;

public interface TodoListDao {

    boolean save(TodoList todoList);
    TodoList merge(TodoList todoList);
    boolean delete(TodoList todoList);
    TodoList getById(long id);
    TodoList getByIdWithTodos(long id);
    TodoList getByIdWithDoneTodos(long id);
    TodoList getByIdWithTodosAndDoneTodos(long id);
    List<TodoList> getByUserId(long id);
    List<TodoList> getByUsers(long id);
    List<User> getUsersByTodoListId(long id);
}
