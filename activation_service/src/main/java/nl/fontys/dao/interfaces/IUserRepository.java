package nl.fontys.dao.interfaces;

import nl.fontys.domain.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IUserRepository extends CrudRepository<User, UUID> { }
