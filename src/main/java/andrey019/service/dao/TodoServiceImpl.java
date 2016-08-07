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
    public boolean addTodo(String email, long todoListId, String todoText) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = getListIfAllowed(user, todoListId);
        if (todoList == null) {
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
    public boolean doneTodo(String email, long todoId) {
        Todo todo = todoDao.getById(todoId);
        if (todo == null) {
            return false;
        }
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = todo.getTodoList();
        if (!todoList.getUsers().contains(user)) {
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
    public boolean unDoneTodo(String email, long doneTodoId) {
        DoneTodo doneTodo = doneTodoDao.getById(doneTodoId);
        if (doneTodo == null) {
            return false;
        }
        User user = userDao.getByEmail(email);
        if (user == null) {
            return false;
        }
        TodoList todoList = doneTodo.getTodoList();
        if (!user.getSharedTodoLists().contains(todoList)) {
            return false;
        }
        if (!todoList.getUser().equals(user)) {
            if (!user.getEmail().equals(doneTodo.getDoneByEmail())) {
                return false;
            }
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
    public boolean deleteTodoList(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        TodoList todoList = getListIfOwner(user, todoListId);
        if (todoList == null) {
            return false;
        }
        user.removeTodoList(todoList);
        return userDao.save(user);
    }

    @Override
    public TodoList getTodoListById(String email, long todoListId) {
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
        System.out.println("equals");
        return todoList;
    }

    @Override
    public List<TodoList> getAllTodoLists(String email) {
        User user = userDao.getByEmailWithSharedLists(email);
        if (user == null) {
            return null;
        }
        return user.getSharedTodoLists();
    }

    @Override
    public List<DoneTodo> getAllDoneTodos(String email, long todoListId) {
        User user = userDao.getByEmail(email);
        TodoList todoList = getListIfAllowed(user, todoListId);
        if (todoList == null) {
            return null;
        }
        return todoList.getDoneTodos();
    }

    @Override
    public List<Todo> getAllTodos(String email, long todoListId) {
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
}
