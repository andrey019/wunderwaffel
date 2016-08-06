package andrey019.model.dao;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fName;

    @Column(nullable = false)
    private String lName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoList> todoLists = new ArrayList<>();

    @ManyToMany(mappedBy = "users", cascade = {CascadeType.MERGE})
    private List<TodoList> sharedTodoLists = new ArrayList<>();

    @Column(nullable = false)
    private String state = State.ACTIVE.getState();

    @Column(nullable = false)
    private String role = Role.USER.getRole();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }

    public List<TodoList> getSharedTodoLists() {
        return sharedTodoLists;
    }

    public void setSharedTodoLists(List<TodoList> sharedTodoLists) {
        this.sharedTodoLists = sharedTodoLists;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFullName() {
        return fName + " " + lName;
    }

    public void setUserFromConfirmation(UserConfirmation userConfirmation) {
        this.email = userConfirmation.getEmail();
        this.password = userConfirmation.getPassword();
        this.fName = userConfirmation.getfName();
        this.lName = userConfirmation.getlName();
    }

    public void addTodoList(TodoList todoList) {
        todoList.setUser(this);
        todoLists.add(todoList);
    }

    public void removeTodoList(TodoList todoList) {
        todoLists.remove(todoList);
        todoList.setUser(null);
    }

    public void addSharedTodoList(TodoList todoList) {
        sharedTodoLists.add(todoList);
        todoList.getUsers().add(this);
    }

    public void removeSharedTodoList(TodoList todoList) {
        sharedTodoLists.remove(todoList);
        todoList.getUsers().remove(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) id;
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof User))
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equalsIgnoreCase(other.email))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", fname=" + fName +
                ", lname=" + lName + ", state=" + state + ", role=" + role +"]";
    }


}