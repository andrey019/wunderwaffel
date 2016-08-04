package andrey019.model.dao;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "todo_list")
public class TodoList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_todo_list",
    joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "todo_list_id", referencedColumnName = "id")})
    private List<User> users;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    private List<Todo> todos;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL)
    private Set<DoneTodo> doneTodos;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Set<DoneTodo> getDoneTodos() {
        return doneTodos;
    }

    public void setDoneTodos(Set<DoneTodo> doneTodos) {
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
