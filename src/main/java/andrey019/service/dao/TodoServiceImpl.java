package andrey019.service.dao;

import andrey019.dao.DoneTodoDao;
import andrey019.dao.TodoDao;
import andrey019.dao.TodoListDao;
import andrey019.dao.UserDao;
import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
import andrey019.service.HtmlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

    private final static String ERROR = "error";
    private final static String OK = "ok";
    private final static String NOT_OWNER = "This list was not created by you. You can not delete it!";

    @Autowired
    private UserDao userDao;

    @Autowired
    private TodoListDao todoListDao;

    @Autowired
    private TodoDao todoDao;

    @Autowired
    private DoneTodoDao doneTodoDao;

    @Autowired
    private HtmlGenerator htmlGenerator;

    @Override
    public boolean addTodoList(String email, String todoListName) {
        TodoList todoList = new TodoList();
        todoList.setName(todoListName);
        User user = userDao.getByEmailWitnListsAndSharedLists(email);
        if (user == null) {
            return false;
        }
        user.addTodoList(todoList);
        user.addSharedTodoList(todoList);
        return userDao.save(user);
    }

    @Override
    public boolean addTodo(String email, long todoListId, String todoText) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = todoListDao.getByIdWithTodos(todoListId);
        if (todoList == null) {
            return false;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return false;
        }
        Todo todo = new Todo();
        todo.setCreatedByEmail(user.getEmail());
        todo.setCreatedByName(user.getFullName());
        todo.setTodoText(todoText);
        todoList.addTodo(todo);
        return todoListDao.save(todoList);
    }

    @Override
    public boolean doneTodo(String email, long todoListId, long todoId) {
        Todo todo = todoDao.getById(todoId);
        if ( (todo == null) || (todo.getTodoList().getId() != todoListId) ) {
            return false;
        }
        User user = userDao.getByEmailWithSharedLists(email);
        if ( (user == null) || (!user.getSharedTodoLists().contains(todo.getTodoList())) ) {
            return false;
        }
        TodoList todoList = todoListDao.getByIdWithTodosAndDoneTodos(todoListId);
        if (todoList == null) {
            return false;
        }
        DoneTodo doneTodo = new DoneTodo();
        doneTodo.setFromTodo(todo);
        doneTodo.setDoneByEmail(user.getEmail());
        doneTodo.setDoneByName(user.getFullName());
        todoList.addDoneTodo(doneTodo);
        todoList.removeTodo(todo);
        return todoListDao.save(todoList);
    }

    @Override
    public boolean unDoneTodo(String email, long todoListId, long doneTodoId) {
        DoneTodo doneTodo = doneTodoDao.getById(doneTodoId);
        if ( (doneTodo == null) || (doneTodo.getTodoList().getId() != todoListId) ) {
            return false;
        }
        User user = userDao.getByEmailWithSharedLists(email);
        if ( (user == null) || (!user.getSharedTodoLists().contains(doneTodo.getTodoList())) ) {
            return false;
        }
        TodoList todoList = todoListDao.getByIdWithTodosAndDoneTodos(todoListId);
        if (todoList == null) {
            return false;
        }
        Todo todo = new Todo();
        todo.setFromDoneTodo(doneTodo);
        todoList.addTodo(todo);
        todoList.removeDoneTodo(doneTodo);
        return todoListDao.save(todoList);
    }

    @Override
    public boolean shareTodoList(String email, long todoListId, String emailToShareWith) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = getListIfAllowed(user, todoListId);
        if (todoList == null) {
            return false;
        }
        User userToShare = userDao.getByEmail(emailToShareWith);
        if (userToShare == null) {
            return false;
        }
        todoList.addUsers(userToShare);
        return todoListDao.save(todoList);
    }

    @Override
    public boolean unShareWith(String email, long todoListId, String emailToUnShareWith) {
        User userToUnShare = userDao.getByEmail(emailToUnShareWith);
        TodoList todoList = getListIfOwner(userToUnShare, todoListId);
        if (todoList != null) {
            return false;
        }
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        todoList = getListIfAllowed(user, todoListId);
        if (todoList == null) {
            return false;
        }
        todoList.removeUsers(userToUnShare);
        return todoListDao.save(todoList);
    }

    @Override
    public String deleteTodoList(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListDao.getByIdWithUsersAndSharedLists(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getOwner().equals(user)) {
            return NOT_OWNER;
        }
        for (User innerUser : todoList.getUsers()) {
            innerUser.getSharedTodoLists().remove(todoList);
        }
        if (!todoListDao.delete(todoList)) {
            return ERROR;
        }
        return OK;
    }

    @Override
    public String getTodoListInfo(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return ERROR;
        }
        List<User> users = userDao.getUsersByTodoListId(todoListId);
        if ( (users == null) || (users.isEmpty()) ) {
            return ERROR;
        }
        if (!users.contains(user)) {
            return ERROR;
        }
        users.remove(user);
        return htmlGenerator.generateTodoListsInfoHtml(users);
    }

    @Override
    public String getSharedWithInfo(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListDao.getByIdWithUsers(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getUsers().contains(user)) {
            return ERROR;
        }
        todoList.getUsers().remove(user);
        if (todoList.getOwner().equals(user)) {
            return htmlGenerator.generateSharedInfoHtml(todoList.getUsers(), null);
        } else {
            todoList.getUsers().remove(todoList.getOwner());
            return htmlGenerator.generateSharedInfoHtml(todoList.getUsers(), todoList.getOwner());
        }
    }

    @Override
    public Set<Todo> getTodosByListId(String email, long todoListId) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getByIdWithTodos(todoListId);
        if (todoList == null) {
            return null;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return null;
        }
        return todoList.getTodos();
    }

    @Override
    public Set<DoneTodo> getDoneTodosByListId(String email, long todoListId) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getByIdWithDoneTodos(todoListId);
        if (todoList == null) {
            return null;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return null;
        }
        return todoList.getDoneTodos();
    }

    @Override
    public Set<TodoList> getAllTodoLists(String email) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return null;
        }
        return user.getSharedTodoLists();
    }

    @Override
    public Set<DoneTodo> getAllDoneTodos(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        TodoList todoList = getListIfAllowed(user, todoListId);
        if (todoList == null) {
            return null;
        }
        return todoList.getDoneTodos();
    }

    @Override
    public Set<Todo> getAllTodos(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        TodoList todoList = getListIfAllowed(user, todoListId);
        if (todoList == null) {
            return null;
        }
        return todoList.getTodos();
    }

    @Override
    public TodoList getListIfOwner(User user, long todoListId) {
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return null;
        }
        if (user.equals(todoList.getOwner())) {
            return todoList;
        }
        return null;
    }

    @Override
    public TodoList getListIfAllowed(User user, long todoListId) {
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return null;
        }
        if (todoList.getUsers().contains(user)) {
            return todoList;
        }
        return null;
    }
}
