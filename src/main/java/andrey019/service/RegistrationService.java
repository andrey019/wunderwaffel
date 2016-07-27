package andrey019.service;


import andrey019.model.User;

public interface RegistrationService {

    public boolean isEmailCorrect(String email);
    public boolean isNotConfirmed(String email);
    public boolean isEmailUnique(String email);
    public String preRegistration(User user);
    public boolean confirmRegistration(String confirmationCode);
}
