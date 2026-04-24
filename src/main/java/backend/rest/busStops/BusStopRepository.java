package backend.rest.busStops;

import java.util.List;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;

import backend.domain.BusStop;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BusStopRepository extends RestfulJpaRepository<BusStop, UUID>{

  @RestResource(exported = false)
  public List<BusStop> findByBusStopName(String busStopName);
}
