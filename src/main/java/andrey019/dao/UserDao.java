package andrey019.dao;

import andrey019.model.User;

public interface UserDao {

    void save(User user);
    User findById(int id);
    User findByEmail(String email);
}