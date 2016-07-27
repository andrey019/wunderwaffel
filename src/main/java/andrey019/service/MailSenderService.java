package andrey019.service;


import andrey019.model.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class MailSenderService extends Thread {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("logService")
    private LogService logService;

    private static MailSenderService mailSenderService = new MailSenderService();
    private static ConcurrentHashMap<CustomMessage, CustomMessage> msgMap = new ConcurrentHashMap<>();
    private final static CustomMessage NULL_MESSAGE = new CustomMessage();

    private MailSenderService() {};

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!msgMap.isEmpty()) {
                CustomMessage message = msgMap.keySet().iterator().next();
                MimeMessagePreparator preparator = getMessagePreparator(message);
                try {
                    mailSender.send(preparator);
                    logService.mailSent(message.getTo());
                    msgMap.remove(message);
                } catch (MailException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            try {
                Thread.sleep(70000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private MimeMessagePreparator getMessagePreparator(final CustomMessage message) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(message.getFrom());
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(message.getTo()));
                mimeMessage.setText(message.getText());
                mimeMessage.setSubject(message.getSubject());
            }
        };
        return preparator;
    }

    public static MailSenderService getInstance() {
        return mailSenderService;
    }

    public void addMessage(CustomMessage message) {
        msgMap.put(message, NULL_MESSAGE);
    }
}