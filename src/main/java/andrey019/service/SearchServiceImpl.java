package andrey019.service;


import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;

public class SearchServiceImpl implements SearchService {

    private final static String TODO_BUTTON_0 = "<button id=\"todo=";
    private final static String TODO_BUTTON_1 = "\" name=\"";
    private final static String TODO_BUTTON_2 = "\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"doneTodo(event)\" style=\"word-wrap: break-word\">";
    private final static String TODO_BUTTON_3 = "<div style=\"font-size:11px; text-align: right\">Created by: ";
    private final static String TODO_BUTTON_4 = ".</div></button>";

    private final static String LIST_BUTTON_0 = "<button id=\"list=";
    private final static String LIST_BUTTON_1 = "\" type=\"button\" class=\"list-group-item\" " +
            "onclick=\"loadTodos(event)\" name=\"";
    private final static String LIST_BUTTON_2 = "\" style=\"word-wrap: break-word\"><span id=\"badge=";
    private final static String LIST_BUTTON_3 = "\" class=\"badge\">";
    private final static String LIST_BUTTON_4 = "</span>";
    private final static String LIST_BUTTON_5 = "</button>";

    @Override
    public String findTodos(User user, String request) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean isFound = false;
        for (TodoList todoList : user.getSharedTodoLists()) {
            for (Todo todo : todoList.getTodos()) {
                if (todo.getTodoText().contains(request)) {
                    addTodo(stringBuilder, todo);
                    isFound = true;
                }
            }
            if (isFound) {
                addListButton(stringBuilder, todoList);
                isFound = false;
            }
        }

        return stringBuilder.toString();
    }

    private void addTodo(StringBuilder stringBuilder, Todo todo) {

    }

    private void addListButton(StringBuilder stringBuilder, TodoList todoList) {

    }
}
