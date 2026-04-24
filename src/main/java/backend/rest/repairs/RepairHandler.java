package backend.rest.repairs;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Repair;
import backend.domain.auditing.AuditedAuditor;
import backend.domain.exception.ResponseStatusExceptions;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class RepairHandler {

  protected Log logger = LogFactory.getLog(getClass());

  @HandleBeforeRead
  public void beforeRead(Repair e, Specification<Repair> spec) throws Exception{
    logger.info("[HandleBeforeRead] : "+ e);
    if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")){
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleAfterRead
  public void afterRead(Repair e, Serializable r) throws Exception{
    logger.info("[HandleafterRead] "+e);
    logger.info("[HandleafterRead] "+r);
  }

  @HandleBeforeCreate
  public void beforeCreate(Repair e) throws Exception{
    logger.info("[HandlebeforeCreate] "+e);
    if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleBeforeSave
  public void beforeSave(Repair e) throws Exception{
    logger.info("[HandlebeforeSave] "+e);
    if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }
  
  @HandleBeforeDelete
  public void beforeDelete(Repair e) throws Exception{
    logger.info("[HandlebeforeDelete] "+e);
    if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")) {
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleAfterCreate
  public void afterCreate(Repair e) throws Exception{
    logger.info("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(Repair e) throws Exception{
    logger.info("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(Repair e) throws Exception{
    logger.info("[HandleafterDelete] "+e);
  }
  
}
