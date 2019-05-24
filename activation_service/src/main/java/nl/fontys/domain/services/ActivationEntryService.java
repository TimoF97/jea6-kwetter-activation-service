package nl.fontys.domain.services;

import nl.fontys.dao.interfaces.IActivationEntryRepository;
import nl.fontys.domain.models.ActivationEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public class ActivationEntryService {

    @Autowired
    private IActivationEntryRepository activationEntryRepository;

    Optional<ActivationEntry> findById(final UUID id) {
        return activationEntryRepository.findById(id);
    }

    ActivationEntry save(final ActivationEntry activationEntry) {
        return activationEntryRepository.save(activationEntry);
    }

    Iterable<ActivationEntry> findAllByExpirationDateIsBefore(final Date date) {
        return activationEntryRepository.findAllByExpirationDateIsBefore(date);
    }

    void deleteById(final UUID id) {
        activationEntryRepository.deleteById(id);
    }
}
