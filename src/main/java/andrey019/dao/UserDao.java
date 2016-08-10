package andrey019.dao;

import andrey019.model.dao.User;

public interface UserDao {

    boolean save(User user);
    User merge(User user);
    User getById(long id);
    User getByEmail(String email);
    User getByEmailWithSharedLists(String email);
    User getByEmailWitnListsAndSharedLists(String email);
}