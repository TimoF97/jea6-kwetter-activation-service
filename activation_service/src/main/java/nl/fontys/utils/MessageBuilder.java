package nl.fontys.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageBuilder {

    private static final Logger LOGGER = Logger.getLogger(MessageBuilder.class.getName());

    public Message buildMessage(final Session session, final String authorEmail, final String recipientEmail, final String subject, final String content) {
        final Message message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(authorEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(content);

            return message;
        } catch (final MessagingException e) {
            LOGGER.log(Level.SEVERE, "An exception was thrown in the MessageBuilder", e);

            return null;
        }
    }
}
