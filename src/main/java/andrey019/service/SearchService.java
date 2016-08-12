package andrey019.service;


import andrey019.model.dao.User;

public interface SearchService {

    String findTodos(User user, String request);
}
