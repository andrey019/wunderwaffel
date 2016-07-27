package andrey019.service;

import andrey019.model.User;

public interface UserService {

    void save(User user);
    User findById(int id);
    User findByEmail(String email);
}