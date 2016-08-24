package andrey019.dao;


import andrey019.model.dao.TodoList;

public interface TodoListDao {

    boolean save(TodoList todoList);
    boolean delete(TodoList todoList);
    TodoList getById(long id);
}
