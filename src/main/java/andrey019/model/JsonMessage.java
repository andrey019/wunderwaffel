package andrey019.model;


public class JsonMessage {

    private long listId;
    private long todoId;
    private long doneTodoId;
    private String shareWith;
    private String unShareWith;

    public long getListId() {
        return listId;
    }

    public void setListId(long listId) {
        this.listId = listId;
    }

    public long getTodoId() {
        return todoId;
    }

    public void setTodoId(long todoId) {
        this.todoId = todoId;
    }

    public long getDoneTodoId() {
        return doneTodoId;
    }

    public void setDoneTodoId(long doneTodoId) {
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

    @Override
    public String toString() {
        return "listId: " + listId + ", todoId: " + todoId + ", doneTodoId: " + doneTodoId +
                ", shareWith: " + shareWith + ", unShareWith: " + unShareWith;
    }
}
