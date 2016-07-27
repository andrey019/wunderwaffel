package andrey019.dao;

import andrey019.model.UserConfirmation;

import java.util.List;


public interface RegistrationDao {

    boolean save(UserConfirmation userConfirmation);
    UserConfirmation getByEmail(String email);
    UserConfirmation getByCode(String code);
    List<UserConfirmation> getByDateOlderThen(long date);
    boolean delete(UserConfirmation userConfirmation);
    void deleteList(List<UserConfirmation> list);
}
