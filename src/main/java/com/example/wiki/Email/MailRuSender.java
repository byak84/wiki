package com.example.wiki.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailRuSender implements EmailSender {

    @Value("${mail.from}")
    private String from;

    @Value("${mail.password}")
    private String password;

    @Override
    public boolean sendMessage(String to, EmailMessage emailMessage) {
        final String smtpHost = "smtp.mail.ru";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", smtpHost);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(emailMessage.getSubject());
            message.setText(emailMessage.getText());
            Transport.send(message);

            System.out.println("Сообщение успешно отправлено....");
            return true;
        } catch (MessagingException e) {
            return false;
        }
    }
}
