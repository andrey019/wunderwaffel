package andrey019.service.dao;


import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;

import java.util.Set;

public interface TodoService {

    boolean addTodoList(String email, String todoListName);
    boolean addTodo(String email, String todoListId, String todoText);
    boolean doneTodo(String email, String todoListId, String todoId);
    boolean unDoneTodo(String email, String todoListId, String doneTodoId);
    boolean shareTodoList(String email, String todoListId, String emailToShareWith);
    boolean unShareWith(String email, String todoListId, String emailToUnShareWith);
    boolean deleteTodoList(String email, String todoListId);
    Set<Todo> getTodosByListId(String email, String todoListId);
    Set<DoneTodo> getDoneTodosByListId(String email, String todoListId);
    Set<TodoList> getAllTodoLists(String email);
    Set<Todo> getAllTodos(String email, String todoListId);
    Set<DoneTodo> getAllDoneTodos(String email, String todoListId);
    TodoList getListIfOwner(User user, long todoListId);
    TodoList getListIfAllowed(User user, long todoListId);
}
