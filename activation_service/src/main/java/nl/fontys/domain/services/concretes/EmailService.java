package nl.fontys.domain.services.concretes;

import nl.fontys.domain.services.interfaces.IEmailService;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService implements IEmailService {

    private static final Logger LOGGER = Logger.getLogger(EmailService.class.getName());

    private static final boolean AUTH = true;
    private static final boolean TTLS = true;
    private static final int PORT = 587;
    private static final String MAIL_SMTP_AUTH_PROPERTY = "mail.smtp.auth";
    private static final String MAIL_SMTP_TTLS_PROPERTY = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_HOST_PROPERTY = "mail.smtp.host";
    private static final String MAIL_SMTP_PORT_PROPERTY = "mail.smtp.port";

    private Properties properties;
    private Session session;

    public IEmailService init(final String email, final String password, final String host) {
        properties = new Properties();
        properties.put(MAIL_SMTP_AUTH_PROPERTY, AUTH);
        properties.put(MAIL_SMTP_TTLS_PROPERTY, TTLS);
        properties.put(MAIL_SMTP_HOST_PROPERTY, host);
        properties.put(MAIL_SMTP_PORT_PROPERTY, PORT);

        session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(email, password);
            }
        });
        return this;
    }

    public void sendEmail(final Message message) {
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            LOGGER.log(Level.SEVERE, "An exception was thrown in the MessageBuilder", e);
        }
    }

    public Session getSession() {
        return session;
    }
}
