package backend.rest.sessions;

import backend.domain.Session;
import io.u2ware.common.data.jpa.repository.RestfulJpaRepository;

public interface SessionRepository extends RestfulJpaRepository<Session, String>{
  
}
