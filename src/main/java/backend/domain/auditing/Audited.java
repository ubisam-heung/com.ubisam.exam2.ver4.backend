package backend.domain.auditing;

import jakarta.persistence.Embeddable;
import lombok.Data;


@Embeddable
public @Data class Audited {

    private String username;
    private String address;
    private Long timestamp;

}
