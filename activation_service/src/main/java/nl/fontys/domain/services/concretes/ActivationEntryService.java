package nl.fontys.domain.services.concretes;

import nl.fontys.dao.interfaces.IActivationEntryRepository;
import nl.fontys.domain.models.ActivationEntry;
import nl.fontys.domain.services.interfaces.IActivationEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivationEntryService implements IActivationEntryService {

    @Autowired
    private IActivationEntryRepository activationEntryRepository;

    public Optional<ActivationEntry> findById(final UUID id) {
        return activationEntryRepository.findById(id);
    }

    public ActivationEntry save(final ActivationEntry activationEntry) {
        return activationEntryRepository.save(activationEntry);
    }

    public Iterable<ActivationEntry> findAllByExpirationDateIsBefore(final Date date) {
        return activationEntryRepository.findAllByExpirationDateIsBefore(date);
    }

    public void deleteById(final UUID id) {
        activationEntryRepository.deleteById(id);
    }
}
