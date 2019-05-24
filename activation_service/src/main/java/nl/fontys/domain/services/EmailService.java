package nl.fontys.domain.services;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;

public class EmailService {

    private static final boolean AUTH = true;
    private static final boolean TTLS = true;
    private static final int PORT = 587;
    private static final String MAIL_SMTP_AUTH_PROPERTY = "mail.smtp.auth";
    private static final String MAIL_SMTP_TTLS_PROPERTY = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_HOST_PROPERTY = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT_PROPERTY = "mail.smtp.port";

    private final String username;
    private final String password;
    private final Properties properties;
    private Session session;

    public EmailService(final String username, final String password, final String host){
        this.username = username;
        this.password = password;

        properties = new Properties();
        properties.put(MAIL_SMTP_AUTH_PROPERTY, AUTH);
        properties.put(MAIL_SMTP_TTLS_PROPERTY, TTLS);
        properties.put(MAIL_SMTP_HOST_PROPERTY, host);
        properties.put(MAIL_SMTP_PORT_PROPERTY, PORT);
    }

    public EmailService init(){
        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });
        return this;
    }

    public void sendEmail(final Message message){
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return session;
    }
}
