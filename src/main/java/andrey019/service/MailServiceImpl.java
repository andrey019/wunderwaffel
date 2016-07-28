package andrey019.service;


import andrey019.model.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("mailService")
public class MailServiceImpl implements MailService {

    private static final String FROM_EMAIL = "wunderwaffelapp@mail.ru";

    @Autowired
    private MailSenderService mailSenderService;


    @Override
    public void sendMail(final CustomMessage message) {
        mailSenderService.addMessage(message);
    }

    @Override
    public void sendMail(String toEmail, String subject, String text) {
        final CustomMessage message = new CustomMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSenderService.addMessage(message);
    }
}
