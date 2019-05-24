package nl.fontys.domain.services.interfaces;

import java.util.UUID;

public interface IActivationService {

    void onUserRegistration(final UUID userId, final String emailAddress);
    void onActivationEntryVisit(final UUID entryId);
    void deleteAllExpiredActivationEntries();
}
