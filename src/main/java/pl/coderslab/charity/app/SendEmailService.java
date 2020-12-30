package pl.coderslab.charity.app;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailService {

    private final JavaMailSender javaMailSender;

    public SendEmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

//    public void sendEmail(String to, String body, String topic){
//
//        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//        simpleMailMessage.setFrom("charitynieboska@gmail.com");
//        simpleMailMessage.setTo(to);
//        simpleMailMessage.setSubject(topic);
//        simpleMailMessage.setText(body);
//        javaMailSender.send(simpleMailMessage);
//
//    }

    public void sendEmailFromContactForm(String body){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("charitynieboska@gmail.com");
        simpleMailMessage.setTo("charitynieboskaContact@gmail.com");
        simpleMailMessage.setSubject("Contact Form");
        simpleMailMessage.setText(body);
        javaMailSender.send(simpleMailMessage);
    }

    public void sendEmail(String to, String body, String topic) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
//            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
//            helper.setTo(to);
//            helper.setFrom("charitynieboska@gmail.com");
//            helper.setSubject(topic);
//            helper.setText(body, true);
            // Set From: header field of the header.
            message.setFrom("charitynieboska@gmail.com");

// Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

// Set Subject: header field
            message.setSubject(topic);

// Now set the actual message
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(message);
    }


}