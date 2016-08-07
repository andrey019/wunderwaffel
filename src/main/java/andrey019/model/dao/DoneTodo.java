package andrey019.model.dao;


import javax.persistence.*;

@Entity
@Table(name = "done_todo")
public class DoneTodo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList todoList;

    @Lob
    @Column(name = "todo_text", nullable = false)
    private String todoText;

    @Column(name = "done_by_name", nullable = false)
    private String doneByName;

    @Column(name = "done_by_email", nullable = false)
    private String doneByEmail;

    @Column(name = "created_by_name", nullable = false)
    private String createdByName;

    @Column(name = "created_by_email", nullable = false)
    private String createdByEmail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TodoList getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoList todoList) {
        this.todoList = todoList;
    }

    public String getTodoText() {
        return todoText;
    }

    public void setTodoText(String todoText) {
        this.todoText = todoText;
    }

    public String getDoneByName() {
        return doneByName;
    }

    public void setDoneByName(String doneByName) {
        this.doneByName = doneByName;
    }

    public String getDoneByEmail() {
        return doneByEmail;
    }

    public void setDoneByEmail(String doneByEmail) {
        this.doneByEmail = doneByEmail;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
    }

    public String getCreatedByEmail() {
        return createdByEmail;
    }

    public void setCreatedByEmail(String createdByEmail) {
        this.createdByEmail = createdByEmail;
    }

    public void setFromTodo(Todo todo) {
        todoText = todo.getTodoText();
        createdByEmail = todo.getCreatedByEmail();
        createdByName = todo.getCreatedByName();
    }
}
