package andrey019.model.dao;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
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

    @Column(name = "todo_amount", nullable = false)
    private int todoAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "user_todo_list",
    joinColumns = {@JoinColumn(name = "todo_list_id", referencedColumnName = "id")},
    inverseJoinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")})
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Todo> todos = new HashSet<>();

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DoneTodo> doneTodos = new HashSet<>();

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

    public int getTodoAmount() {
        return todoAmount;
    }

    public void setTodoAmount(int todoAmount) {
        this.todoAmount = todoAmount;
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

    public void addTodo(Todo todo) {
        todo.setTodoList(this);
        todos.add(todo);
        todoAmount = todos.size();
    }

    public void removeTodo(Todo todo) {
        todos.remove(todo);
        todo.setTodoList(null);
        todoAmount = todos.size();
    }

    public void addDoneTodo(DoneTodo doneTodo) {
        doneTodo.setTodoList(this);
        doneTodos.add(doneTodo);
    }

    public void removeDoneTodo(DoneTodo doneTodo) {
        doneTodos.remove(doneTodo);
        doneTodo.setTodoList(null);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof TodoList))
            return false;
        TodoList other = (TodoList) obj;
        if (id != other.getId())
            return false;
        if (hashCode() != other.hashCode()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "id: " + id + ", name: " + name + ", user: [" + user + "]";
    }
}
