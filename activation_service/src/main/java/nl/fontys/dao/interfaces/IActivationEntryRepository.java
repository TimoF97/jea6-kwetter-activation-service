package nl.fontys.dao.interfaces;

import nl.fontys.domain.models.ActivationEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.UUID;

public interface IActivationEntryRepository extends CrudRepository<ActivationEntry, UUID> {

    Iterable<ActivationEntry> findAllByExpirationDateIsBefore(final Date date);
}
