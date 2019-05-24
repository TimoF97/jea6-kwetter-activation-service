package nl.fontys;

import nl.fontys.domain.services.EmailService;
import nl.fontys.utils.MessageBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.Message;

@SpringBootApplication
public class ActivationApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ActivationApplication.class, args);
        final EmailService emailService = new EmailService("kwettercasus@gmail.com", "fontysict", "smtp.gmail.com").init();
        final MessageBuilder messageBuilder = new MessageBuilder();
        final Message message = messageBuilder.buildMessage(emailService.getSession(),
                                                 "kwettercasus@gmail.com",
                                                "kwettercasus@gmail.com",
                                                    "Kwetter",
                                                    "Casus");
        emailService.sendEmail(message);
    }
}
