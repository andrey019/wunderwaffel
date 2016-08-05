package andrey019.service.dao;


import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;

import java.util.List;

public interface TodoService {

    boolean addTodoList(String email, String todoListName);
    boolean addTodo(String email, long todoListId, String todoText);
    boolean doneTodo(String email, long todoId);
    boolean unDoneTodo(String email, long doneTodoId);
    boolean shareTodoList(String email, long todoListId, String emailToShareWith);
    boolean unShareWith(String email, long todoListId, String emailToUnShareWith);
    boolean deleteTodoList(String email, long todoListId);
    TodoList getTodoListById(String email, long todoListId);
    List<TodoList> getAllTodoLists(String email);
    User isUserOwner(String email, long todoListId);
    User isUserAllowed(String email, long todoListId);
}
