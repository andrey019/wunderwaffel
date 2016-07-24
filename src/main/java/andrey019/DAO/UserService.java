package andrey019.dao;

import andrey019.model.User;

public interface UserService {

    User findById(int id);
    User findBySso(String sso);
}