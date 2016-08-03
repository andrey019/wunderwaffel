package andrey019.model.dao;


import javax.persistence.*;
import java.util.Set;

public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @OneToMany(mappedBy = "todo_list_id")
    private Set<Todo> todos;

    @OneToMany(mappedBy = "todo_list_id")
    private Set<DoneTodo> doneTodos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Set<Todo> getTodos() {
        return todos;
    }

    public void setTodos(Set<Todo> todos) {
        this.todos = todos;
    }

    public Set<DoneTodo> getDoneTodos() {
        return doneTodos;
    }

    public void setDoneTodos(Set<DoneTodo> doneTodos) {
        this.doneTodos = doneTodos;
    }
}
