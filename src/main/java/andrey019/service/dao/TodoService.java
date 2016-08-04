package andrey019.service.dao;


import andrey019.model.dao.TodoList;

public interface TodoService {

    boolean createTodoList(String email, String todoListName);
    boolean createTodo(String email, long todoListId, String todoText);
    boolean doneTodo(String email, long todoId);
    boolean unDoneTodo(String email, long doneTodoId);
    boolean shareTodoList(String email, long todoListId, String emailToShareWith);
    boolean unShareWith(String email, long todoListId, String emailToUnShareWith);
    boolean deleteTodoList(String email, long todoListId);
    TodoList getTodoListById(String email, long todoListId);
}
