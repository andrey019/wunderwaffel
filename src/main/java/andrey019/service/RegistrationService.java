package andrey019.service;


public interface RegistrationService {

//    boolean isEmailCorrect(String email);
//    boolean isAlreadyWaitingConfirmation(String email);
//    boolean isEmailUnique(String email);
    String preRegistrationCheck(String email);
    boolean preRegistration(String email, String password);
    boolean confirmRegistration(String code);
}
