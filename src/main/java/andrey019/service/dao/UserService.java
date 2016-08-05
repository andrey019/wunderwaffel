package andrey019.service.dao;

import andrey019.model.dao.User;

public interface UserService {

    void save(User user);
    User getById(long id);
    User getByEmail(String email);
}