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
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

    private final static String ERROR = "error";
    private final static String OK = "ok";
    private final static String NOT_OWNER = "This list was not created by you. You can not delete it!";
    private final static String USER_NOT_FOUND = "There is no such user!";
    private final static String EMAIL_NOT_VALID = "Email is not valid!";
    private final static String ALREDY_HAVE = "You already have this list!";

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

    @Autowired
    private EmailValidator emailValidator;

    @Transactional
    @Override
    public boolean addTodoList(String email, String todoListName) {
        TodoList todoList = new TodoList();
        todoList.setName(todoListName);
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        user.addTodoList(todoList);
        user.addSharedTodoList(todoList);
        return userDao.save(user);
    }

    @Transactional
    @Override
    public boolean addTodo(String email, long todoListId, String todoText) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = todoListDao.getById(todoListId);
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

    @Transactional
    @Override
    public boolean doneTodo(String email, long todoListId, long todoId) {
        Todo todo = todoDao.getById(todoId);
        if ( (todo == null) || (todo.getTodoList().getId() != todoListId) ) {
            return false;
        }
        User user = userDao.getByEmail(email);
        if ( (user == null) || (!user.getSharedTodoLists().contains(todo.getTodoList())) ) {
            return false;
        }
        TodoList todoList = todoListDao.getById(todoListId);
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

    @Transactional
    @Override
    public boolean unDoneTodo(String email, long todoListId, long doneTodoId) {
        DoneTodo doneTodo = doneTodoDao.getById(doneTodoId);
        if ( (doneTodo == null) || (doneTodo.getTodoList().getId() != todoListId) ) {
            return false;
        }
        User user = userDao.getByEmail(email);
        if ( (user == null) || (!user.getSharedTodoLists().contains(doneTodo.getTodoList())) ) {
            return false;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return false;
        }
        Todo todo = new Todo();
        todo.setFromDoneTodo(doneTodo);
        todoList.addTodo(todo);
        todoList.removeDoneTodo(doneTodo);
        return todoListDao.save(todoList);
    }

    @Transactional
    @Override
    public String shareWith(String email, long todoListId, String emailToShareWith) {
        if (!emailValidator.isValid(emailToShareWith)) {
            return EMAIL_NOT_VALID;
        }
        if (email.equals(emailToShareWith)) {
            return ALREDY_HAVE;
        }
        User userToShare = userDao.getByEmail(emailToShareWith);
        if (userToShare == null) {
            return USER_NOT_FOUND;
        }
        User user = userDao.getByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getUsers().contains(user)) {
            return ERROR;
        }
        todoList.addUsers(userToShare);
        if (!todoListDao.save(todoList)) {
            return ERROR;
        }
        return OK;
    }

    @Transactional
    @Override
    public String unShareWith(String email, long todoListId, long idToUnShareWith) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return ERROR;
        }
        User userToUnShare = userDao.getById(idToUnShareWith);
        if (userToUnShare == null) {
            return ERROR;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if ( (!todoList.getUsers().contains(user)) || (todoList.getOwner().equals(userToUnShare))) {
            return ERROR;
        }
        todoList.removeUsers(userToUnShare);
        if (!todoListDao.save(todoList)) {
            return ERROR;
        }
        return OK;
    }

    @Transactional
    @Override
    public String deleteTodoList(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getOwner().equals(user)) {
            return NOT_OWNER;
        }
        todoList.getUsers().size();
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

    @Transactional
    @Override
    public String getSharedWithInfo(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return ERROR;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return ERROR;
        }
        if (!todoList.getUsers().contains(user)) {
            return ERROR;
        }
        Set<User> users = todoList.getUsers();
        users.remove(user);
        if (todoList.getOwner().equals(user)) {
            return htmlGenerator.generateSharedInfoHtml(users, null);
        } else {
            users.remove(todoList.getOwner());
            return htmlGenerator.generateSharedInfoHtml(users, todoList.getOwner());
        }
    }

    @Transactional
    @Override
    public Set<Todo> getTodosByListId(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return null;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return null;
        }
        todoList.getTodos().size();
        return todoList.getTodos();
    }

    @Transactional
    @Override
    public Set<DoneTodo> getDoneTodosByListId(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getById(todoListId);
        if (todoList == null) {
            return null;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return null;
        }
        todoList.getDoneTodos().size();
        return todoList.getDoneTodos();
    }

    @Transactional
    @Override
    public Set<TodoList> getAllTodoLists(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return null;
        }
        user.getSharedTodoLists().size();
        return user.getSharedTodoLists();
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
