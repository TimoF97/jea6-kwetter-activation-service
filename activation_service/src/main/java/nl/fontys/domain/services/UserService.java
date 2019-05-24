package nl.fontys.domain.services;

import nl.fontys.dao.interfaces.IUserRepository;
import nl.fontys.domain.models.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class UserService {

    @Autowired
    private IUserRepository userRepository;

    User save(final User user) {
        return userRepository.save(user);
    }

    void deleteById(final UUID userId) {
        userRepository.deleteById(userId);
    }
}
