package nl.fontys.domain.services.interfaces;

import javax.mail.Message;
import javax.mail.Session;

public interface IEmailService {

    Session getSession();
    void sendEmail(final Message message);
}
