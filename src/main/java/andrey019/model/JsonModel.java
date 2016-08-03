package andrey019.model;


public class JsonModel {

    private int listId;
    private int todoId;

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getTodoId() {
        return todoId;
    }

    public void setTodoId(int todoId) {
        this.todoId = todoId;
    }

    @Override
    public String toString() {
        return "listId: " + listId + ", todoId: " + todoId;
    }
}
