package andrey019.service;


import andrey019.dao.RegistrationDao;
import org.springframework.beans.factory.annotation.Autowired;

public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationDao registrationDao;

    @Override
    public boolean isEmailCorrect(String email) {
        return false;
    }

    @Override
    public boolean isAlreadyWaitingConfirmation(String email) {
        return false;
    }

    @Override
    public boolean isEmailUnique(String email) {
        return false;
    }

    @Override
    public String preRegistrationCheck(String email) {
        return null;
    }

    @Override
    public boolean preRegistration(String email, String password) {
        return false;
    }

    @Override
    public boolean confirmRegistration(String confirmationCode) {
        return false;
    }
}
