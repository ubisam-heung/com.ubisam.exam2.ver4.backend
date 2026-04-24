package backend.domain;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name = "example_bus_driver")
public class BusDriver {

  @Id
  @GeneratedValue
  private UUID id;

  private String busDriverName;
  private String busDriverLicense;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String keyword;
  
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String option;
}
