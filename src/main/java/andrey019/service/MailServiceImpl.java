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

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Qualifier("logService")
    private LogService logService;

    @Override
    public void sendMail(final Object object) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                CustomMessage message = (CustomMessage) object;

                MimeMessagePreparator preparator = getMessagePreparator(message);

                try {
                    mailSender.send(preparator);
                    logService.mailSent(message.getTo());
                } catch (MailException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

//    private void mail(final Object object) {
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                CustomMessage message = (CustomMessage) object;
//
//                MimeMessagePreparator preparator = getMessagePreparator(message);
//
//                try {
//                    mailSender.send(preparator);
//                    System.out.println("Messsdage Send...Hurrey");
//                } catch (MailException ex) {
//                    System.err.println(ex.getMessage());
//                }
//            }
//        });
//        thread.setDaemon(true);
//        thread.start();
//    }

    private MimeMessagePreparator getMessagePreparator(final CustomMessage message) {

        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            public void prepare(MimeMessage mimeMessage) throws Exception {
                mimeMessage.setFrom(message.getFrom());
                mimeMessage.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(message.getTo()));
                mimeMessage.setText(message.getText());
                mimeMessage.setSubject(message.getSubject());
            }
        };
        return preparator;
    }
}