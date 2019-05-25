package nl.fontys.domain.services.concretes;

import nl.fontys.domain.models.ActivationEntry;
import nl.fontys.domain.models.User;
import nl.fontys.domain.services.interfaces.IActivationEntryService;
import nl.fontys.domain.services.interfaces.IActivationService;
import nl.fontys.domain.services.interfaces.IEmailService;
import nl.fontys.domain.services.interfaces.IUserService;
import nl.fontys.messaging.gateways.APIGateway;
import nl.fontys.utils.MessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ActivationService implements IActivationService {

    private static final Logger LOGGER = Logger.getLogger(ActivationService.class.getName());
    private static final String WEBSITE_URL_PREFIX = "http://localhost:9090/api/v1/activation/";

    @Value("${mail-service.email.address}")
    private String emailAddress;

    @Autowired
    private IActivationEntryService activationEntryService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private IUserService userService;
    private MessageBuilder messageBuilder;
    private APIGateway apiGateway;

    public ActivationService() {
        messageBuilder = new MessageBuilder();

        initGateway();
    }

    private void initGateway() {
        apiGateway = new APIGateway() {

            @Override
            public void onUserRegistration(final UUID userId, final String userEmailAddress) {
                handleUserRegistration(userId, userEmailAddress);
            }

            @Override
            public void onActivationEntryVisit(final UUID entryId) {
                handleActivationEntryVisit(entryId);
            }
        };
    }

    private void deleteEntry(final ActivationEntry entry) {
        LOGGER.log(Level.INFO, "[ActivationService] Locally deleting both the User and ActivationEntry correlated to: " + entry);

        activationEntryService.deleteById(entry.getId());
        userService.deleteById(entry.getUser().getId());
    }

    public void handleUserRegistration(final UUID userId, final String userEmailAddress) {
        final User user = new User(userId, userEmailAddress);
        final ActivationEntry activationEntry = new ActivationEntry(user);

        userService.save(user);
        activationEntryService.save(activationEntry);

        final Message message = messageBuilder.buildMessage(emailService.getSession(),
                this.emailAddress,
                userEmailAddress,
                "Kwetter account activation",
                WEBSITE_URL_PREFIX + activationEntry.getId());

        emailService.sendEmail(message);
    }

    public void handleActivationEntryVisit(final UUID entryId) {
        final Optional<ActivationEntry> optionalEntry = activationEntryService.findById(entryId);

        if (!optionalEntry.isPresent()) {
            LOGGER.log(Level.WARNING, "[ActivationService] No ActivationEntry exists for the given entryId.");
            return;
        }

        apiGateway.sendUserActivatedNotice(optionalEntry.get().getUser().getId());
        deleteEntry(optionalEntry.get());
    }

    public void deleteAllExpiredActivationEntries() {
        final Date currentDateTime = Calendar.getInstance().getTime();
        final Iterable<ActivationEntry> entries = activationEntryService
                .findAllByExpirationDateIsBefore(currentDateTime);

        entries.forEach(entry -> {
            final UUID userId = entry.getUser().getId();

            LOGGER.log(Level.INFO, "[ActivationService] Sending deletion request to API for User with Id: " + userId);
            apiGateway.sendUserDeletionRequest(entry.getUser().getId());

            deleteEntry(entry);
        });
    }
}
