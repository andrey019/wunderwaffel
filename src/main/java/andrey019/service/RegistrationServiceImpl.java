package andrey019.service;


import andrey019.dao.RegistrationDao;
import andrey019.dao.UserDao;
import andrey019.model.User;
import andrey019.model.UserConfirmation;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service("registrationService")
public class RegistrationServiceImpl implements RegistrationService {

    private final static String SUBJECT_TEMPLATE = "WunderWaffel registration confirmation";
    private final static String TEXT_TEMPLATE = "<html><body>You were trying to register an account on WunderWaffel," +
            "to confirm please click on the link below...<br>" +
            "<a href=\"http://wunderwaffel-andrey019.rhcloud.com/auth/confirm?code=%s\">" +
            "Click here to confirm registration</a><br><br>" +
            "If you don't know what's happening, just ignore this message.</body></html>";


    @Autowired
    private RegistrationDao registrationDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Override
    public UserConfirmation getByEmail(String email) {
        return registrationDao.getByEmail(email);
    }

    @Override
    public String preRegistrationCheck(String email) {
        if (!isEmailCorrect(email)) {
            return "Email is incorrect!";
        }
        if (isEmailWaiting(email) || isEmailUsed(email)) {
            return "Email is already in use!";
        }
        return null;
    }

    @Override
    public boolean preRegistration(String email, String password) {
        UserConfirmation userConfirmation = new UserConfirmation();
        userConfirmation.setEmail(email);
        userConfirmation.setPassword(passwordEncoder.encode(password));
        userConfirmation.setCode(passwordEncoder.encode(email + System.currentTimeMillis()));
        userConfirmation.setDate(System.currentTimeMillis());
        if (registrationDao.save(userConfirmation)) {
            mailService.sendMail(userConfirmation.getEmail(), SUBJECT_TEMPLATE,
                    String.format(TEXT_TEMPLATE, userConfirmation.getCode()));
            return true;
        }
        return false;
    }

    @Override
    public boolean confirmRegistration(String code) {
        UserConfirmation userConfirmation = registrationDao.getByCode(code);
        if (userConfirmation == null) {
            return false;
        }
        User user = new User();
        user.setUserFromConfirmation(userConfirmation);
        if (userDao.save(user)) {
            return true;
        }
        return false;
    }

    private boolean isEmailCorrect(String email) {
        return EmailValidator.getInstance().isValid(email);
    }

    private boolean isEmailWaiting(String email) {
        if (registrationDao.getByEmail(email) == null) {
            return false;
        }
        return true;
    }

    private boolean isEmailUsed(String email) {
        if (userDao.findByEmail(email) == null) {
            return false;
        }
        return true;
    }
}
