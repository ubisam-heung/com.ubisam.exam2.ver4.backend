package backend.rest.sessions;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Session;
import backend.domain.auditing.AuditedAuditor;
import backend.domain.exception.ResponseStatusExceptions;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class SessionHandler {

  protected Log logger = LogFactory.getLog(getClass());

  @HandleBeforeCreate
  public void handleBeforeCreate(Session e) throws Exception{
    logger.info("[HandleBeforeCreate] : "+ e);
    throw ResponseStatusExceptions.NOT_FOUND;
  }

  @HandleBeforeSave
  public void handleBeforeSave(Session e) throws Exception{
    logger.info("[HandleBeforeSave] : "+ e);
    throw ResponseStatusExceptions.NOT_FOUND;
  }

  @HandleBeforeDelete
  public void handleBeforeDelete(Session e) throws Exception{
    throw ResponseStatusExceptions.NOT_FOUND;
  }

  @HandleBeforeRead
  public void handleBeforeRead(Session e, Specification<Session> s) throws Exception{
    logger.info("[HandleBeforeRead] : "+ e);
    if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")){
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleAfterRead
  public void handleAfterRead(Session e, Serializable s) throws Exception{
    logger.info("[HandleAfterRead] : "+ e);
    throw ResponseStatusExceptions.NOT_FOUND;
  }

}
