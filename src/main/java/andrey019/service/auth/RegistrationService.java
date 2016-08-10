package andrey019.service.auth;


import andrey019.model.dao.UserConfirmation;

public interface RegistrationService {

    String preRegistrationCheck(String email);
    String registration(String email, String password, String fName, String lName);
    boolean confirmRegistration(String code);
    UserConfirmation getByEmail(String email);
}
