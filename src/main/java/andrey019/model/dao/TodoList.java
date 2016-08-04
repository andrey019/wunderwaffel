package andrey019.model.dao;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "todo_list")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_todo_list",
    joinColumns = {@JoinColumn(name = "todo_list_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    private List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    private List<DoneTodo> doneTodos = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addUsers(User user) {
        users.add(user);
        user.getSharedTodoLists().add(this);
    }

    public void removeUsers(User user) {
        users.remove(user);
        user.getSharedTodoLists().remove(this);
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public List<DoneTodo> getDoneTodos() {
        return doneTodos;
    }

    public void setDoneTodos(List<DoneTodo> doneTodos) {
        this.doneTodos = doneTodos;
    }

    public void addTodo(Todo todo) {
        todo.setTodoList(this);
        todos.add(todo);
    }

    public void addDoneTodo(DoneTodo doneTodo) {
        doneTodo.setTodoList(this);
        doneTodos.add(doneTodo);
    }
}
