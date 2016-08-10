package andrey019.service.auth;


import andrey019.dao.UserDao;
import andrey019.model.dao.User;
import andrey019.service.MailService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("passwordRecovery")
public class PasswordRecoveryImpl implements PasswordRecovery {

    private final static String MAIL_SUBJECT = "WunderWaffel password recovery";
    private final static String MAIL_TEXT_0 = "<html><body>Your new password for WunderWaffel is <b>";
    private final static String MAIL_TEXT_1 = "</b><br>Please, change it as soon as possible. " +
            "You can do it in your profile menu.";
    private final static String NOT_FOUND = "Email is not found!";
    private final static String ERROR = "Internal error!";
    private final static String OK = "ok";

    @Autowired
    private UserDao userDao;

    @Autowired
    private MailService mailService;

    @Override
    public String generateNewPassword(String email) {
        User user = userDao.getByEmail(email);
        if (user == null) {
            return NOT_FOUND;
        }
        String password = RandomStringUtils.random(10, true, true);
        user.setPassword(password);
        if (userDao.save(user)) {
            mailService.sendMail(email, MAIL_SUBJECT, getMailText(password));
            return OK;
        }
        return ERROR;
    }

    private String getMailText(String password) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MAIL_TEXT_0);
        stringBuilder.append(password);
        stringBuilder.append(MAIL_TEXT_1);
        return stringBuilder.toString();
    }
}
