package backend.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import backend.domain.properties.AttributesSet;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "example_bus")
public class Bus {

  @Id
  @GeneratedValue
  private UUID id;

  private Integer busNumber;

  @Column(length = 1024 * 100)
  private AttributesSet busType;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @RestResource(exported = false)
  @JsonProperty(access = Access.READ_ONLY)
  private BusRoute busRoute;

  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private Link busRouteLink;

  @ManyToOne
  @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @RestResource(exported = false)
  @JsonProperty(access = Access.READ_ONLY)
  private BusDriver busDriver;

  @Transient
  @JsonProperty(access = Access.WRITE_ONLY)
  private Link busDriverLink;

  @ElementCollection
  @CollectionTable(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), name = "example_bus_repair_history")
  private Set<BusRepairHistory> busRepairHistory = new HashSet<>();

  @Embeddable
  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class BusRepairHistory{
    private String busRepairName;
    private String busRepairState;
    private String busRepairTimestamp;
  }

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String keyword;
  
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String option;
  
}
