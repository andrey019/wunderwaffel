package andrey019.service;

import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;

import java.util.List;


public class HtmlGeneratorImpl implements HtmlGenerator {

    private final static String LIST_BUTTON = "<button id=\"list=%s\" type=\"button\" class=\"list-group-item\" style=\"word-wrap: break-word\">%s</button>";
    private final static String DELETE_BUTTON = "<button id=\"del=%s\" type=\"button\" class=\"list-group-item\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button>";
    private final static String TODO_BUTTON = "<button id=\"todo=%s\" type=\"button\" class=\"list-group-item\" style=\"word-wrap: break-word\">%s</button>";
    private final static String DONE_TODO_BUTTON = "<button id=\"done=%s\" type=\"button\" class=\"list-group-item\" style=\"word-wrap: break-word\">%s</button>";
    private final static String NEW_LINE = "<br>";


    @Override
    public String generateTodoListsHtml(List<TodoList> todoLists) {
        return null;
    }

    @Override
    public String generateTodosHtml(List<Todo> todos) {
        return null;
    }
}
