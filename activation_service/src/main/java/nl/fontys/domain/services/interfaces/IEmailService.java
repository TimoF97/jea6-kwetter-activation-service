package nl.fontys.domain.services.interfaces;

import javax.mail.Message;
import javax.mail.Session;

public interface IEmailService {

    IEmailService init(final String email, final String password, final String host);
    Session getSession();
    void sendEmail(final Message message);
}
