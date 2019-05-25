package nl.fontys.messaging.gateways;

import com.timo.messaging.kafka.*;
import com.timo.messaging.models.UserDeletionRequest;
import com.timo.messaging.models.UserRegistration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class APIGateway {

    private static final Logger LOGGER = Logger.getLogger(APIGateway.class.getName());
    private static final String CONSUMER_GROUP_ID = "registration.consumer-group";

    private final MessageBodyParser messageBodyParser;

    private KafkaConsumerImpl<UserRegistration> consumer;
    private KafkaProducerImpl<UserDeletionRequest> producer;

    protected APIGateway() {
        messageBodyParser = new MessageBodyParser();

        initConsumer();
        initProducer();
    }

    private void initConsumer() {
        consumer = new KafkaConsumerImpl<>();
        consumer.setTopics(new LinkedList<>(Collections.singletonList(KafkaConstants.USER_REGISTRATION_TOPIC)))
                .setCallback(message -> {
                    LOGGER.log(Level.INFO, "[APIGateway] Received UserRegistration: " + message);

                    final UserRegistration userRegistration = messageBodyParser.parseBodyFromMessage(message, UserRegistration.class);
                    onUserRegistration(userRegistration.getUserId(), userRegistration.getUserEmailAddress());
                })
                .setConsumerGroup(CONSUMER_GROUP_ID)
                .start();
    }

    private void initProducer() {
        producer = new KafkaProducerImpl<>();
    }

    public void sendUserDeletionRequest(final UUID userId) {
        final UserDeletionRequest userDeletionRequest = new UserDeletionRequest(userId);

        LOGGER.log(Level.INFO, "[ActivationServiceGateway] Sending UserDeletionRequest: " + userDeletionRequest);
        producer.sendMessage(new KafkaMessage<>(userDeletionRequest), KafkaConstants.USER_DELETION_TOPIC);
    }

    public abstract void onUserRegistration(final UUID userId, final String userEmailAddress);
}
