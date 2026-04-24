package backend.domain;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.hateoas.Link;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
@Table(name = "example_bus_route")
public class BusRoute {

  @Id
  @GeneratedValue
  private UUID id;

  private String busRouteName;
  private String busRouteStart;
  private String busRouteEnd;

  @ManyToMany
  @JoinTable(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT), inverseForeignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  @RestResource(exported = false)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Set<BusStop> busStops;

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private Set<Link> busStopLinks = new HashSet<>();

  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String keyword;
  
  @Transient
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String option;
  
}
