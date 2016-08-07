package andrey019.service;


import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;

import java.util.List;
import java.util.Set;

public interface HtmlGenerator {

    String generateTodoListsHtml(List<TodoList> todoLists);
//    String generateTodoListsDeleteHtml(List<TodoList> todoLists);
    String generateTodosHtml(Set<Todo> todos);
    String generateDoneTodosHtml(Set<DoneTodo> doneTodos);
}
