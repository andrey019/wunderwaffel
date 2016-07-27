package andrey019.dao;

import andrey019.model.UserConfirmation;

/**
 * Created by andrey on 27.07.2016.
 */
public interface RegistrationDao {

    public boolean isEmailUnique(String email);
    public boolean isAlreadyWaitingConfirmation(String email);
    public void saveToConfirmationWaitList(UserConfirmation userConfirmation);
    public UserConfirmation getFromConfirmationWaitList(String email);
    public void deleteFromComfirmationWaitList(UserConfirmation userConfirmation);
}
