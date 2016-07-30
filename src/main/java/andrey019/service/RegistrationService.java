package andrey019.service;


import andrey019.model.UserConfirmation;

public interface RegistrationService {

//    boolean isEmailCorrect(String email);
//    boolean isAlreadyWaitingConfirmation(String email);
//    boolean isEmailUnique(String email);
    String preRegistrationCheck(String email);
    boolean preRegistration(String email, String password);
    boolean confirmRegistration(String code);
    UserConfirmation getByEmail(String email);
}
