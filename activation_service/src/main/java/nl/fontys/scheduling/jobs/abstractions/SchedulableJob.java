package nl.fontys.scheduling.jobs.abstractions;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class SchedulableJob implements Runnable {

    private final ScheduledExecutorService scheduledExecutorService;

    protected SchedulableJob() {
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
    }

    public void schedule(final int thresholdInMinutes) {
        scheduledExecutorService.scheduleAtFixedRate(this, 0, thresholdInMinutes, TimeUnit.MINUTES);
    }
}
