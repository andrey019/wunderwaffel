package andrey019.dao;


import andrey019.model.dao.TodoList;

import java.util.List;

public interface TodoListDao {

    boolean save(TodoList todoList);
    boolean delete(TodoList todoList);
    TodoList getById(long id);
    List<TodoList> getByUserId(long id);
    List<TodoList> getByUsers(long id);
}
