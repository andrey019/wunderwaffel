package andrey019.service.dao;


import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;

import java.util.Set;

public interface TodoService {

    boolean addTodoList(String email, String todoListName);
    boolean addTodo(String email, long todoListId, String todoText);
    boolean doneTodo(String email, long todoListId, long todoId);
    boolean unDoneTodo(String email, long todoListId, long doneTodoId);
    boolean shareTodoList(String email, long todoListId, String emailToShareWith);
    String unShareWith(String email, long todoListId, String emailToUnShareWith);
    String deleteTodoList(String email, long todoListId);
    String getTodoListInfo(String email, long todoListId);
    String getSharedWithInfo(String email, long todoListId);
    Set<Todo> getTodosByListId(String email, long todoListId);
    Set<DoneTodo> getDoneTodosByListId(String email, long todoListId);
    Set<TodoList> getAllTodoLists(String email);
    Set<Todo> getAllTodos(String email, long todoListId);
    Set<DoneTodo> getAllDoneTodos(String email, long todoListId);
    TodoList getListIfOwner(User user, long todoListId);
    TodoList getListIfAllowed(User user, long todoListId);
}
