package andrey019.service;

import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("htmlGenerator")
public class HtmlGeneratorImpl implements HtmlGenerator {

    private final static String LIST_BUTTON = "<button id=\"list=%d\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"loadTodos(event)\" name=\"%s\" style=\"word-wrap: break-word\">" +
            "<span id=\"badge=%d\" class=\"badge\">%d</span>%s</button>";
//    private final static String DELETE_BUTTON = "<button id=\"del=%d\" type=\"button\" class=\"list-group-item\">" +
//            "<span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button>";
    private final static String TODO_BUTTON = "<button id=\"todo=%d\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"doneTodo(event)\" style=\"word-wrap: break-word\">%s" +
            "<div style=\"font-size:11px; text-align: right\">Created by: %s.</div></button>";
    private final static String DONE_TODO_BUTTON = "<button id=\"done=%d\" type=\"button\" " +
            "class=\"list-group-item\" onclick=\"unDoneTodo(event)\" style=\"word-wrap: break-word; " +
            "background-color: lightgrey\"><s>%s</s>" +
            "<div style=\"font-size:11px; text-align: right\">Created by: %s. Done by: %s.</div></button>";


    private final static String LIST_BUTTON_0 = "<button id=\"list=";
    private final static String LIST_BUTTON_1 = "\" type=\"button\" class=\"list-group-item\" onclick=\"loadTodos(event)\" name=\"";
    private final static String LIST_BUTTON_2 = "\" style=\"word-wrap: break-word\"><span id=\"badge=";
    private final static String LIST_BUTTON_3 = "\" class=\"badge\">";
    private final static String LIST_BUTTON_4 = "</span>";
    private final static String LIST_BUTTON_5 = "</button>";


    private final static String NEW_LINE = "<br>";
    private final static int MAX_SYMBOLS_IN_LINE = 17;


    @Override
    public String generateTodoListsHtml(Set<TodoList> todoLists) {
        StringBuilder stringBuilder = new StringBuilder();
        long time = System.currentTimeMillis();
        for (TodoList todoList : todoLists) {
            stringBuilder.append(String.format(LIST_BUTTON, todoList.getId(), todoList.getName(), todoList.getId(),
                    todoList.getTodoAmount(), addBreaks(todoList.getName())));
        }
        System.out.println(System.currentTimeMillis() - time);


        StringBuilder stringBuilder1 = new StringBuilder();
        time = System.currentTimeMillis();
        for (TodoList todoList : todoLists) {
            stringBuilder1.append(LIST_BUTTON_0);
            stringBuilder1.append(todoList.getId());
            stringBuilder1.append(LIST_BUTTON_1);
            stringBuilder1.append(todoList.getName());
            stringBuilder1.append(LIST_BUTTON_2);
            stringBuilder1.append(todoList.getId());
            stringBuilder1.append(LIST_BUTTON_3);
            stringBuilder1.append(todoList.getTodoAmount());
            stringBuilder1.append(LIST_BUTTON_4);
            stringBuilder1.append(addBreaks(todoList.getName()));
            stringBuilder1.append(LIST_BUTTON_5);
        }
        System.out.println(System.currentTimeMillis() - time);
        return stringBuilder1.toString();
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
    public String generateTodosHtml(Set<Todo> todos) {
        if (todos.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
//        StringBuilder testBuilder = new StringBuilder();
        for (Todo todo : todos) {
            stringBuilder.append(String.format(TODO_BUTTON, todo.getId(), todo.getTodoText(),
                    todo.getCreatedByName()));
//            testBuilder.append(todo.getId());
//            testBuilder.append(" / ");
        }
//        System.out.println(testBuilder.toString());
//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    @Override
    public String generateDoneTodosHtml(Set<DoneTodo> doneTodos) {
        if (doneTodos.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (DoneTodo doneTodo : doneTodos) {
            stringBuilder.append(String.format(DONE_TODO_BUTTON, doneTodo.getId(), doneTodo.getTodoText(),
                    doneTodo.getCreatedByName(), doneTodo.getDoneByName()));
        }
        return stringBuilder.toString();
    }

    private String addBreaks(String text) {
        if (text.length() <= MAX_SYMBOLS_IN_LINE) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder(text);
        stringBuilder.insert(MAX_SYMBOLS_IN_LINE, NEW_LINE);
        return stringBuilder.toString();
    }
}
