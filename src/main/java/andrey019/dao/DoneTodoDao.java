package andrey019.dao;


import andrey019.model.dao.DoneTodo;

import java.util.List;

public interface DoneTodoDao {

    boolean save(DoneTodo todo);
    boolean delete(DoneTodo todo);
    List<DoneTodo> getByCreatedEmail(String email);
}
