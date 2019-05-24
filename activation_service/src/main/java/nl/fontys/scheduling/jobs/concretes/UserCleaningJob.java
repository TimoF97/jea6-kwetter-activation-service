package nl.fontys.scheduling.jobs.concretes;

import nl.fontys.domain.services.ActivationService;
import nl.fontys.scheduling.jobs.abstractions.SchedulableJob;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserCleaningJob extends SchedulableJob {

    private static final Logger LOGGER = Logger.getLogger(UserCleaningJob.class.getName());

    private ActivationService activationService;

    public UserCleaningJob() {
        super();

        activationService = new ActivationService();
    }

    @Override
    public void run() {
        try {
            LOGGER.log(Level.INFO, "[UserCleaningJob] Starting cycle.");
            activationService.deleteAllExpiredActivationEntries();
        } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, "[UserCleaningJob] An exception has been thrown during execution of the current cycle.", e);
        }
    }
}
