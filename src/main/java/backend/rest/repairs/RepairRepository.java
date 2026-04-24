package backend.rest.repairs;

import java.util.UUID;

import backend.domain.Repair;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface RepairRepository extends RestfulJpaRepository<Repair, UUID>{
  
  
}
