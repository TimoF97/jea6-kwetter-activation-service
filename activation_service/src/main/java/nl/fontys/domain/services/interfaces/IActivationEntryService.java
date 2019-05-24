package nl.fontys.domain.services.interfaces;

import nl.fontys.domain.models.ActivationEntry;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public interface IActivationEntryService {

    Optional<ActivationEntry> findById(final UUID id);
    ActivationEntry save(final ActivationEntry activationEntry);
    Iterable<ActivationEntry> findAllByExpirationDateIsBefore(final Date date);
    void deleteById(final UUID id);
}
