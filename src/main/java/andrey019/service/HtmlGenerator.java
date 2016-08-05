package andrey019.service;


import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;

import java.util.List;

public interface HtmlGenerator {

    String generateTodoListsHtml(List<TodoList> todoLists);
//    String generateTodoListsDeleteHtml(List<TodoList> todoLists);
    String generateTodosHtml(List<Todo> todos);
    String generateDoneTodosHtml(List<DoneTodo> doneTodos);
}
