package andrey019.model;


public class JsonMessage {

    private String listId;
    private String todoId;
    private String doneTodoId;
    private String shareWith;
    private String unShareWith;
    private String todoText;

    public String getListId() {
        return listId;
    }

    public void setListId(String listId) {
        this.listId = listId;
    }

    public String getTodoId() {
        return todoId;
    }

    public void setTodoId(String todoId) {
        this.todoId = todoId;
    }

    public String getDoneTodoId() {
        return doneTodoId;
    }

    public void setDoneTodoId(String doneTodoId) {
        this.doneTodoId = doneTodoId;
    }

    public String getShareWith() {
        return shareWith;
    }

    public void setShareWith(String shareWith) {
        this.shareWith = shareWith;
    }

    public String getUnShareWith() {
        return unShareWith;
    }

    public void setUnShareWith(String unShareWith) {
        this.unShareWith = unShareWith;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    @Override
    public String toString() {
        return "listId: " + listId + ", todoId: " + todoId + ", doneTodoId: " + doneTodoId +
                ", shareWith: " + shareWith + ", unShareWith: " + unShareWith + ", todoText: " + todoText;
    }
}
