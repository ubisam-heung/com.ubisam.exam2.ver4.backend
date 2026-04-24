package backend.rest.busDrivers;

import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;

import backend.domain.BusDriver;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;
import java.util.List;


public interface BusDriverRepository extends RestfulJpaRepository<BusDriver, UUID>{

  @RestResource(exported = false)
  public List<BusDriver> findByBusDriverName(String busDriverName);
  
}
