package andrey019.service.dao;

import andrey019.dao.UserDao;
import andrey019.model.dao.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void save(User user) {
        dao.save(user);
    }

    public User getById(long id) {
        return dao.getById(id);
    }

    public User getByEmail(String email) {
        return dao.getByEmail(email);
    }

}