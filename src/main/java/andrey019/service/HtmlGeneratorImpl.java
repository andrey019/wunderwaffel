package andrey019.service;

import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("htmlGenerator")
public class HtmlGeneratorImpl implements HtmlGenerator {

    private final static String LIST_BUTTON = "<button id=\"list=%d\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"loadTodos(event)\" style=\"word-wrap: break-word\">" +
            "<span id=\"badge=%d\" class=\"badge\">%d</span>%s</button>";
//    private final static String DELETE_BUTTON = "<button id=\"del=%d\" type=\"button\" class=\"list-group-item\">" +
//            "<span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button>";
    private final static String TODO_BUTTON = "<button id=\"todo=%d\" type=\"button\" class=\"list-group-item\" " +
            "style=\"word-wrap: break-word\">%s<br>Created by: %s</button>";
    private final static String DONE_TODO_BUTTON = "<button id=\"done=%d\" type=\"button\" class=\"list-group-item\" " +
            "style=\"word-wrap: break-word\">%s<br>Created by: %s. Done by: %s</button>";
//    private final static String NEW_LINE = "<br>";
//    private final static int MAX_SYMBOLS_IN_LINE = 14;


    @Override
    public String generateTodoListsHtml(List<TodoList> todoLists) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TodoList todoList : todoLists) {
            stringBuilder.append(String.format(LIST_BUTTON, todoList.getId(), todoList.getId(),
                    todoList.getTodos().size(), todoList.getName()));
        }
        return stringBuilder.toString();
    }

//    @Override
//    public String generateTodoListsDeleteHtml(List<TodoList> todoLists) {
//        StringBuilder stringBuilder = new StringBuilder();
//        for (TodoList todoList : todoLists) {
//            stringBuilder.append(String.format(DELETE_BUTTON, todoList.getId()));
//            for (int i = 1; i <= (todoList.getName().length() / MAX_SYMBOLS_IN_LINE); i++) {
//                stringBuilder.append(NEW_LINE);
//            }
//        }
//        return stringBuilder.toString();
//    }

    @Override
    public String generateTodosHtml(List<Todo> todos) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Todo todo : todos) {
            stringBuilder.append(String.format(TODO_BUTTON, todo.getId(), todo.getTodoText(),
                    todo.getCreatedByName()));
        }
        return stringBuilder.toString();
    }

    @Override
    public String generateDoneTodosHtml(List<DoneTodo> doneTodos) {
        StringBuilder stringBuilder = new StringBuilder();
        for (DoneTodo doneTodo : doneTodos) {
            stringBuilder.append(String.format(DONE_TODO_BUTTON, doneTodo.getId(), doneTodo.getTodoText(),
                    doneTodo.getCreatedByName(), doneTodo.getDoneByName()));
        }
        return stringBuilder.toString();
    }
}
