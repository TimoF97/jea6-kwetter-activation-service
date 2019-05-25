package nl.fontys.domain.services.interfaces;

import java.util.UUID;

public interface IActivationService {

    void handleUserRegistration(final UUID userId, final String emailAddress);
    void handleActivationEntryVisit(final UUID entryId);
    void deleteAllExpiredActivationEntries();
}
