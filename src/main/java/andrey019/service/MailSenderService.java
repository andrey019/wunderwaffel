package andrey019.service;


import andrey019.model.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MailSenderService extends Thread {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("logService")
    private LogService logService;

    private static MailSenderService mailSenderService = new MailSenderService();
    private static ConcurrentLinkedQueue<CustomMessage> queue = new ConcurrentLinkedQueue<>();

    private MailSenderService() {}

    @Override
    public void run() {
        while (!isInterrupted()) {
            if (!queue.isEmpty()) {
                CustomMessage message = queue.peek();
                MimeMessagePreparator preparator = getMessagePreparator(message);
                try {
                    mailSender.send(preparator);
                    queue.poll();
                    logService.mailSent(message.getTo(), queue.size());
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
        queue.add(message);
    }
}
