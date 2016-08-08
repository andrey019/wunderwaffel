package andrey019.service.dao;

import andrey019.dao.DoneTodoDao;
import andrey019.dao.TodoDao;
import andrey019.dao.TodoListDao;
import andrey019.dao.UserDao;
import andrey019.model.dao.DoneTodo;
import andrey019.model.dao.Todo;
import andrey019.model.dao.TodoList;
import andrey019.model.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service("todoService")
public class TodoServiceImpl implements TodoService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TodoListDao todoListDao;

    @Autowired
    private TodoDao todoDao;

    @Autowired
    private DoneTodoDao doneTodoDao;

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
        if (userDao.save(user)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addTodo(String email, String todoListId, String todoText) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = todoListDao.getByIdWithTodos(getLongId(todoListId));
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
    public boolean doneTodo(String email, String todoListIdStr, String todoId) {
        Todo todo = todoDao.getById(getLongId(todoId));
        System.out.println("doneTodo todoDao.getbyid");
        System.out.println(todo + " / " + todoId);
        long todoListId = getLongId(todoListIdStr);
        if ( (todo == null) || (todo.getTodoList().getId() != todoListId) ) {
            return false;
        }
        User user = userDao.getByEmailWithSharedLists(email);
        System.out.println("doneTodo userDao.getbyemailwith...");
        if ( (user == null) || (!user.getSharedTodoLists().contains(todo.getTodoList())) ) {
            return false;
        }
        TodoList todoList = todoListDao.getByIdWithTodosAndDoneTodos(todoListId);
        System.out.println("doneTodo todoListDao.getbyidwith...");
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
    public boolean unDoneTodo(String email, String todoListIdStr, String doneTodoId) {
        DoneTodo doneTodo = doneTodoDao.getById(getLongId(doneTodoId));
        System.out.println("unDoneTodo doneTodoDao.getbyid");
        long todoListId = getLongId(todoListIdStr);
        if ( (doneTodo == null) || (doneTodo.getTodoList().getId() != todoListId) ) {
            return false;
        }
        User user = userDao.getByEmailWithSharedLists(email);
        System.out.println("unDoneTodo userDao.getbyemailwith...");
        if ( (user == null) || (!user.getSharedTodoLists().contains(doneTodo.getTodoList())) ) {
            return false;
        }
        TodoList todoList = todoListDao.getByIdWithTodosAndDoneTodos(todoListId);
        System.out.println("unDoneTodo todoListDao.getbyidwith...");
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
    public boolean shareTodoList(String email, String todoListId, String emailToShareWith) {    // TODO: 08.08.16 string to long id
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = getListIfAllowed(user, getLongId(todoListId));
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
    public boolean unShareWith(String email, String todoListIdStr, String emailToUnShareWith) {    // TODO: 08.08.16 string to long id
        User userToUnShare = userDao.getByEmail(emailToUnShareWith);
        long todoListId = getLongId(todoListIdStr);
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
    public boolean deleteTodoList(String email, String todoListId) {    // TODO: 08.08.16 string to long id
        User user = userDao.getByEmail(email);
        TodoList todoList = getListIfOwner(user, getLongId(todoListId));
        if (todoList == null) {
            return false;
        }
        user.removeTodoList(todoList);
        return userDao.save(user);
    }

    @Override
    public Set<Todo> getTodosByListId(String email, String todoListId) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getByIdWithTodos(getLongId(todoListId));
        if (todoList == null) {
            return null;
        }
        if (!user.getSharedTodoLists().contains(todoList)) {
            return null;
        }
        return todoList.getTodos();
    }

    @Override
    public Set<DoneTodo> getDoneTodosByListId(String email, String todoListId) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return null;
        }
        TodoList todoList = todoListDao.getByIdWithDoneTodos(getLongId(todoListId));
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
    public Set<DoneTodo> getAllDoneTodos(String email, String todoListId) {
        User user = userDao.getByEmail(email);
        TodoList todoList = getListIfAllowed(user, getLongId(todoListId));
        if (todoList == null) {
            return null;
        }
        return todoList.getDoneTodos();
    }

    @Override
    public Set<Todo> getAllTodos(String email, String todoListId) {
        User user = userDao.getByEmail(email);
        TodoList todoList = getListIfAllowed(user, getLongId(todoListId));
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
        if (user.equals(todoList.getUser())) {
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

    private long getLongId(String id) {
        try {
            long longId = Long.valueOf(id.split("=")[1]);
            return longId;
        } catch (Exception ex) {
            return 0;
        }
    }
}
