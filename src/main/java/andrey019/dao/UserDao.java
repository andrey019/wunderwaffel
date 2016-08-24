package andrey019.dao;

import andrey019.model.dao.User;

import java.util.List;

public interface UserDao {

    boolean save(User user);

    User getById(long id);
    User getByEmail(String email);
    List<User> getUsersByTodoListId(long id);
}