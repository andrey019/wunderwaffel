package andrey019.service.dao;


import andrey019.model.dao.UserConfirmation;

public interface RegistrationService {

    String preRegistrationCheck(String email);
    boolean preRegistration(String email, String password);
    boolean confirmRegistration(String code);
    UserConfirmation getByEmail(String email);
}
