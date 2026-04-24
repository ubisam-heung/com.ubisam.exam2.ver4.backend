package backend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "example_session")
public class Session {

  @Id
  private String principal;

  private String state;
  private Long timestamp;
  
}
