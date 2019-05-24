package nl.fontys.domain.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class User {

    @Id
    private UUID id;
    private String emailAddress;

    protected User() { }

    public User(final UUID id, final String emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
    }
}
