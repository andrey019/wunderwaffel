package andrey019.service;


import andrey019.model.CustomMessage;

public interface MailService {

    public void sendMail(final CustomMessage message);
}
