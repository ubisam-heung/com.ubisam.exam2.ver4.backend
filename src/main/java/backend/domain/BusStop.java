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
@Table(name = "example_bus_stop")
public class BusStop {

  @Id
  @GeneratedValue
  private UUID id;

  private String busStopName;
  private String busStopLocation;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String keyword;
  
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String option;
}
