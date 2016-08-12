package andrey019.service;

import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("htmlGenerator")
public class HtmlGeneratorImpl implements HtmlGenerator {

    private final static String LIST_BUTTON_0 = "<button id=\"list=";
    private final static String LIST_BUTTON_1 = "\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"loadTodos(event)\" name=\"";
    private final static String LIST_BUTTON_2 = "\" style=\"word-wrap: break-word\"><span id=\"badge=";
    private final static String LIST_BUTTON_3 = "\" class=\"badge\">";
    private final static String LIST_BUTTON_4 = "</span>";
    private final static String LIST_BUTTON_5 = "</button>";

    private final static String TODO_BUTTON_0 = "<button id=\"todo=";
    private final static String TODO_BUTTON_1 = "\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"doneTodo(event)\" style=\"word-wrap: break-word\">";
    private final static String TODO_BUTTON_2 = "<div style=\"font-size:11px; text-align: right\">Created by: ";
    private final static String TODO_BUTTON_3 = ".</div></button>";

    private final static String DONE_TODO_BUTTON_0 = "<button id=\"done=";
    private final static String DONE_TODO_BUTTON_1 = "\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"unDoneTodo(event)\" style=\"word-wrap: break-word; background-color: lightgrey\"><s>";
    private final static String DONE_TODO_BUTTON_2 = "</s><div style=\"font-size:11px; " +
            "text-align: right\">Created by: ";
    private final static String DONE_TODO_BUTTON_3 = ". Done by: ";
    private final static String DONE_TODO_BUTTON_4 = ".</div></button>";

    private final static String LIST_INFO_0 = "<button type=\"button\" class=\"list-group-item " +
            "list-group-item-warning\" disabled>";
    private final static String LIST_INFO_1 = "</button>";

    private final static String SHARE_INFO_0 = "<button id=\"unShare=";
    private final static String SHARE_INFO_1 = "\" type=\"button\" class=\"list-group-item list-group-item-danger\" " +
            "onclick=\"unShareUser(event)\" style=\"word-wrap: break-word\"";
    private final static String SHARE_INFO_2 = " disabled";
    private final static String SHARE_INFO_3 = ">";
    private final static String SHARE_INFO_4 = "</button>";

    private final static String NEW_LINE = "<br>";
    private final static int MAX_SYMBOLS_IN_LINE = 17;
    private final static String EMPTY = "";


    @Override
    public String generateTodoListsHtml(Set<TodoList> todoLists) {
        if (todoLists.isEmpty()) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (TodoList todoList : todoLists) {
            stringBuilder.append(LIST_BUTTON_0);
            stringBuilder.append(todoList.getId());
            stringBuilder.append(LIST_BUTTON_1);
            stringBuilder.append(todoList.getName());
            stringBuilder.append(LIST_BUTTON_2);
            stringBuilder.append(todoList.getId());
            stringBuilder.append(LIST_BUTTON_3);
            stringBuilder.append(todoList.getTodoAmount());
            stringBuilder.append(LIST_BUTTON_4);
            stringBuilder.append(todoList.getName());
            stringBuilder.append(LIST_BUTTON_5);
        }
        return stringBuilder.toString();
    }

    @Override
    public String generateTodosHtml(Set<Todo> todos) {
        if (todos.isEmpty()) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Todo todo : todos) {
            stringBuilder.append(TODO_BUTTON_0);
            stringBuilder.append(todo.getId());
            stringBuilder.append(TODO_BUTTON_1);
            stringBuilder.append(todo.getTodoText());
            stringBuilder.append(TODO_BUTTON_2);
            stringBuilder.append(todo.getCreatedByName());
            stringBuilder.append(TODO_BUTTON_3);
        }
        return stringBuilder.toString();
    }

    @Override
    public String generateDoneTodosHtml(Set<DoneTodo> doneTodos) {
        if (doneTodos.isEmpty()) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (DoneTodo doneTodo : doneTodos) {
            stringBuilder.append(DONE_TODO_BUTTON_0);
            stringBuilder.append(doneTodo.getId());
            stringBuilder.append(DONE_TODO_BUTTON_1);
            stringBuilder.append(doneTodo.getTodoText());
            stringBuilder.append(DONE_TODO_BUTTON_2);
            stringBuilder.append(doneTodo.getCreatedByName());
            stringBuilder.append(DONE_TODO_BUTTON_3);
            stringBuilder.append(doneTodo.getDoneByName());
            stringBuilder.append(DONE_TODO_BUTTON_4);
        }
        return stringBuilder.toString();
    }

    @Override
    public String generateTodoListsInfoHtml(List<User> users) {
        if (users.isEmpty()) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (User user : users) {
            stringBuilder.append(LIST_INFO_0);
            stringBuilder.append(user.getEmail());
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(user.getFullName());
            stringBuilder.append(LIST_INFO_1);
        }
        return stringBuilder.toString();
    }

    @Override
    public String generateSharedInfoHtml(Set<User> users, User owner) {
        if ( (owner == null) && (users.isEmpty()) ) {
            return EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (owner != null) {
            stringBuilder.append(SHARE_INFO_0);
            stringBuilder.append(owner.getId());
            stringBuilder.append(SHARE_INFO_1);
            stringBuilder.append(SHARE_INFO_2);
            stringBuilder.append(SHARE_INFO_3);
            stringBuilder.append(owner.getEmail());
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(owner.getFullName());
            stringBuilder.append(SHARE_INFO_4);
        }
        for(User user : users) {
            stringBuilder.append(SHARE_INFO_0);
            stringBuilder.append(user.getId());
            stringBuilder.append(SHARE_INFO_1);
            stringBuilder.append(SHARE_INFO_3);
            stringBuilder.append(user.getEmail());
            stringBuilder.append(NEW_LINE);
            stringBuilder.append(user.getFullName());
            stringBuilder.append(SHARE_INFO_4);
        }
        return stringBuilder.toString();
    }

    //    private String addBreaks(String text) {
//        if (text.length() <= MAX_SYMBOLS_IN_LINE) {
//            return text;
//        }
//        StringBuilder stringBuilder = new StringBuilder(text);
//        stringBuilder.insert(MAX_SYMBOLS_IN_LINE, NEW_LINE);
//        return stringBuilder.toString();
//    }
}
