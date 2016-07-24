package andrey019.dao;

import andrey019.model.User;

public interface UserDao {

    User findById(int id);
    User findBySSO(String sso);
}