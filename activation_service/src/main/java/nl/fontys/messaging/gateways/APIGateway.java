package nl.fontys.messaging.gateways;

import com.timo.messaging.kafka.KafkaConstants;
import com.timo.messaging.kafka.KafkaConsumerImpl;
import com.timo.messaging.kafka.MessageBodyParser;
import com.timo.messaging.models.UserRegistration;

import java.util.Collections;
import java.util.LinkedList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class APIGateway {

    private static final Logger LOGGER = Logger.getLogger(APIGateway.class.getName());
    private final MessageBodyParser messageBodyParser;
    private KafkaConsumerImpl<UserRegistration> consumer;

    protected APIGateway() {
        messageBodyParser = new MessageBodyParser();

        initConsumer();
    }

    private void initConsumer() {
        consumer = new KafkaConsumerImpl<>();
        consumer.setTopics(new LinkedList<>(Collections.singletonList(KafkaConstants.USER_REGISTRATION_TOPIC)))
                .setCallback(message -> {
                    LOGGER.log(Level.INFO, "[APIGateway] Received UserRegistration: " + message);

                    final UserRegistration userRegistration = messageBodyParser.parseBodyFromMessage(message, UserRegistration.class);
                    onUserRegistration(userRegistration.getUserId(), userRegistration.getUserEmailAddress());
                })
                .start();
    }

    public abstract void onUserRegistration(final UUID userId, final String userEmailAddress);
}
