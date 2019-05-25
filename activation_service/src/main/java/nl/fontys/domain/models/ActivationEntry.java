package nl.fontys.domain.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
public class ActivationEntry {

    private static final int EXPIRATION_TIME_IN_MS = 120_000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne
    private User user;

    private Date expirationDate;

    protected ActivationEntry() { }

    public ActivationEntry(final User user) {
        this.user = user;

        expirationDate = Calendar.getInstance().getTime();
        expirationDate.setTime(expirationDate.getTime() + EXPIRATION_TIME_IN_MS);
    }
}
