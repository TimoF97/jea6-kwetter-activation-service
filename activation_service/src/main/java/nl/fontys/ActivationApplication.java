package nl.fontys;

import nl.fontys.scheduling.jobs.concretes.UserCleaningJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ActivationApplication {

    public static void main(final String[] args) {
        // TODO; Messaging init
        // TODO: ActivationService aanroepen
        SpringApplication.run(ActivationApplication.class, args);

        new UserCleaningJob().schedule(1);
    }
}
