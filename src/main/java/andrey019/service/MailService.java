package andrey019.service;


import andrey019.model.CustomMessage;

public interface MailService {

    void sendMail(final CustomMessage message);
    void sendMail(String toEmail, String subject, String text);
}
