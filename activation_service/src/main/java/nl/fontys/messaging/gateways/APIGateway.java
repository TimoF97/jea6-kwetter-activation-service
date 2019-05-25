package nl.fontys.messaging.gateways;

import com.timo.messaging.kafka.*;
import com.timo.messaging.models.ActivationEntryVisitNotice;
import com.timo.messaging.models.UserActivatedNotice;
import com.timo.messaging.models.UserDeletionRequest;
import com.timo.messaging.models.UserRegistrationNotice;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class APIGateway {

    private static final Logger LOGGER = Logger.getLogger(APIGateway.class.getName());
    private static final String USER_REGISTRATION_CONSUMER_GROUP_ID = "user.registration.consumer-group";
    private static final String ACTIVATION_ENTRY_CONSUMER_GROUP_ID = "activation.entry.consumer-group";

    private final MessageBodyParser messageBodyParser;

    private KafkaConsumerImpl<UserRegistrationNotice> userRegistrationConsumer;
    private KafkaConsumerImpl<ActivationEntryVisitNotice> activationEntryVisitConsumer;
    private KafkaProducerImpl<UserDeletionRequest> userDeletionProducer;
    private KafkaProducerImpl<UserActivatedNotice> activatedUserProducer;

    protected APIGateway() {
        messageBodyParser = new MessageBodyParser();

        initConsumers();
        initProducers();
    }

    private void initConsumers() {
        userRegistrationConsumer = new KafkaConsumerImpl<>();
        userRegistrationConsumer.setTopics(new LinkedList<>(Collections.singletonList(KafkaConstants.USER_REGISTRATION_TOPIC)))
                .setCallback(message -> {
                    LOGGER.log(Level.INFO, "[APIGateway] Received UserRegistrationNotice: " + message);

                    final UserRegistrationNotice userRegistration = messageBodyParser.parseBodyFromMessage(message, UserRegistrationNotice.class);
                    onUserRegistration(userRegistration.getUserId(), userRegistration.getUserEmailAddress());
                })
                .setConsumerGroup(USER_REGISTRATION_CONSUMER_GROUP_ID)
                .start();

        activationEntryVisitConsumer = new KafkaConsumerImpl<>();
        activationEntryVisitConsumer.setTopics(new LinkedList<>(Collections.singletonList(KafkaConstants.ACTIVATION_ENTRY_VISIT_TOPIC)))
                .setCallback(message -> {
                    LOGGER.log(Level.INFO, "[APIGateway] Received ActivationEntryVisitNotice: " + message);

                    final ActivationEntryVisitNotice activationEntryVisitNotice = messageBodyParser.parseBodyFromMessage(message, ActivationEntryVisitNotice.class);
                    onActivationEntryVisit(activationEntryVisitNotice.getEntryId());
                })
                .setConsumerGroup(ACTIVATION_ENTRY_CONSUMER_GROUP_ID)
                .start();
    }

    private void initProducers() {
        userDeletionProducer = new KafkaProducerImpl<>();
        activatedUserProducer = new KafkaProducerImpl<>();
    }

    public void sendUserDeletionRequest(final UUID userId) {
        final UserDeletionRequest userDeletionRequest = new UserDeletionRequest(userId);

        LOGGER.log(Level.INFO, "[ActivationServiceGateway] Sending UserDeletionRequest: " + userDeletionRequest);
        userDeletionProducer.sendMessage(new KafkaMessage<>(userDeletionRequest), KafkaConstants.USER_DELETION_TOPIC);
    }

    public void sendUserActivatedNotice(final UUID userId) {
        final UserActivatedNotice userActivatedNotice = new UserActivatedNotice(userId);

        LOGGER.log(Level.INFO, "[ActivationServiceGateway] Sending UserActivatedNotice: " + userActivatedNotice);
        activatedUserProducer.sendMessage(new KafkaMessage<>(userActivatedNotice), KafkaConstants.USER_ACTIVATED_NOTICE_TOPIC);
    }

    public abstract void onUserRegistration(final UUID userId, final String userEmailAddress);

    public abstract void onActivationEntryVisit(final UUID entryId);
}
