package nl.fontys.domain.services.interfaces;

import nl.fontys.domain.models.User;

import java.util.UUID;

public interface IUserService {

    User save(final User user);
    void deleteById(final UUID userId);
}
