package backend.domain.auditing;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public @Data abstract class AuditedEntity {

    @CreatedBy
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(name = "inserted_username")),
            @AttributeOverride(name = "address", column = @Column(name = "inserted_address")),
            @AttributeOverride(name = "timestamp", column = @Column(name = "inserted_timestamp"))
    })
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Audited inserted;

    @LastModifiedBy
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "username", column = @Column(name = "updated_username")),
            @AttributeOverride(name = "address", column = @Column(name = "updated_address")),
            @AttributeOverride(name = "timestamp", column = @Column(name = "updated_timestamp"))
    })
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    protected Audited updated;

}
