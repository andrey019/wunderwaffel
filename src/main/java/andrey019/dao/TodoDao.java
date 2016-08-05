package andrey019.dao;


import andrey019.model.dao.Todo;

import java.util.List;

public interface TodoDao {

    boolean save(Todo todo);
    boolean delete(Todo todo);
    List<Todo> getByCreatedEmail(String email);
    Todo getById(long id);
}
