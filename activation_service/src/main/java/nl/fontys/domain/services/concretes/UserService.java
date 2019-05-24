package nl.fontys.domain.services.concretes;

import nl.fontys.dao.interfaces.IUserRepository;
import nl.fontys.domain.models.User;
import nl.fontys.domain.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    public User save(final User user) {
        return userRepository.save(user);
    }

    public void deleteById(final UUID userId) {
        userRepository.deleteById(userId);
    }
}
