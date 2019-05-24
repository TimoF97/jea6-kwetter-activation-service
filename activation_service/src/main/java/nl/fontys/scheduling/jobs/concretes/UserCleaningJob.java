package nl.fontys.scheduling.jobs.concretes;

import nl.fontys.domain.services.ActivationEntryService;
import nl.fontys.scheduling.jobs.abstractions.SchedulableJob;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserCleaningJob extends SchedulableJob {

    private static final Logger LOGGER = Logger.getLogger(UserCleaningJob.class.getName());

    @Autowired
    private ActivationEntryService activationEntryService;

    public UserCleaningJob() {
        super();
    }

    @Override
    public void run() {
        LOGGER.log(Level.INFO, "[UserCleaningJob] Starting cycle.");
        activationEntryService.deleteAllExpiredActivationEntries();
    }
}
