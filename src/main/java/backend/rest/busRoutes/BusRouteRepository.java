package backend.rest.busRoutes;

import java.util.List;
import java.util.UUID;

import org.springframework.data.rest.core.annotation.RestResource;

import backend.domain.BusRoute;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface BusRouteRepository extends RestfulJpaRepository<BusRoute, UUID>{

  @RestResource(exported = false)
  List<BusRoute> findByBusRouteName(String busRouteName);
  
}
