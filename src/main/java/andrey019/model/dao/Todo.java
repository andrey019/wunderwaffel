package andrey019.model.dao;


import javax.persistence.*;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_list_id", nullable = false)
    private TodoList todoList;

    @Lob
    @Column(name = "todo_text", nullable = false)
    private String todoText;

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

    public void setFromDoneTodo(DoneTodo doneTodo) {
        todoText = doneTodo.getTodoText();
        createdByEmail = doneTodo.getCreatedByEmail();
        createdByName = doneTodo.getCreatedByName();
    }
}
