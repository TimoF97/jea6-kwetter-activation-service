package nl.fontys.domain.services;

import nl.fontys.domain.models.ActivationEntry;
import nl.fontys.domain.models.User;
import nl.fontys.utils.MessageBuilder;

import javax.mail.Message;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActivationService {

    private static final Logger LOGGER = Logger.getLogger(ActivationService.class.getName());

    private static final String EMAIL_ADDRESS = "kwettercasus@gmail.com";
    private static final String EMAIL_PASSWORD = "fontysict";
    private static final String EMAIL_HOST = "smtp.gmail.com";
    private static final String WEBSITE_URL_PREFIX = "http://localhost:9090/activation/";

    private ActivationEntryService activationEntryService;
    private EmailService emailService;
    private UserService userService;
    private MessageBuilder messageBuilder;

    public ActivationService() {
        this.activationEntryService = new ActivationEntryService();
        this.emailService = new EmailService(EMAIL_ADDRESS, EMAIL_PASSWORD, EMAIL_HOST).init();
        this.userService = new UserService();
        this.messageBuilder = new MessageBuilder();
    }

    public void onUserRegistration(final UUID userId, final String emailAddress) {
        final User user = new User(userId, emailAddress);
        final ActivationEntry activationEntry = new ActivationEntry(user);

        userService.save(user);
        activationEntryService.save(activationEntry);

        final Message message = messageBuilder.buildMessage(emailService.getSession(),
                EMAIL_ADDRESS,
                emailAddress,
                "Kwetter account activation",
                WEBSITE_URL_PREFIX + activationEntry.getId());
        emailService.sendEmail(message);
    }

    public void onActivationEntryVisit(final UUID entryId) {
        final Optional<ActivationEntry> optionalEntry = activationEntryService.findById(entryId);

        if (!optionalEntry.isPresent()) {
            LOGGER.log(Level.WARNING, "[ActivationEntryService] No ActivationEntry exists for the given entryId.");
            return;
        }

        deleteEntry(optionalEntry.get());

        // TODO: kwetter api melden dat de user activated is zodat in api isActivated naar true gaat
    }

    // TODO: messaging met gateway
    // TODO: in kwetter api isActivated by user toevoegen + onMEssage maken voor activated user

    public void deleteAllExpiredActivationEntries() {
        final Date currentDateTime = Calendar.getInstance().getTime();
        final Iterable<ActivationEntry> entries = activationEntryService
                .findAllByExpirationDateIsBefore(currentDateTime);

        entries.forEach(entry -> {
            deleteEntry(entry);

            // TODO: via messaging kwetter api user laten deleten
        });
    }

    private void deleteEntry(final ActivationEntry entry) {
        activationEntryService.deleteById(entry.getId());
        userService.deleteById(entry.getUser().getId());
    }
}
