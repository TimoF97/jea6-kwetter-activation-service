package nl.fontys.scheduling.jobs.concretes;

import nl.fontys.domain.services.interfaces.IActivationService;
import nl.fontys.scheduling.jobs.abstractions.SchedulableJob;
import nl.fontys.utils.BeanUtils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UserCleaningJob extends SchedulableJob {

    private static final Logger LOGGER = Logger.getLogger(UserCleaningJob.class.getName());

    private IActivationService activationService;

    public UserCleaningJob() {
        super();

        activationService = BeanUtils.getBean(IActivationService.class);
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
