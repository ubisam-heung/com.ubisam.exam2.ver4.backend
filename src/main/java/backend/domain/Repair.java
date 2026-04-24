package backend.domain;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_repair")
public class Repair {

  @Id
  @GeneratedValue
  private UUID id;

  private String principal;
  private String state;
  private Long timestamp;
  
}
