package backend.rest.accounts;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Account;
import backend.domain.auditing.AuditedAuditor;
import backend.domain.exception.ResponseStatusExceptions;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class AccountHandler {

  protected Log logger = LogFactory.getLog(getClass());

  @HandleBeforeCreate
  public void handleBeforeCreate(Account e) throws Exception{
    logger.info("[HandleBeforeCreate] : "+ e);
    throw ResponseStatusExceptions.UNAUTHORIZED;
  }

  @HandleBeforeSave
  public void handleBeforeSave(Account e) throws Exception{
    logger.info("[HandleBeforeSave] : "+ e);
    if(!AuditedAuditor.hasPermission("ROLE_ADMIN") && !e.getId().equals(AuditedAuditor.getCurrentUsername())){
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleBeforeDelete
  public void handleBeforeDelete(Account e) throws Exception{
    logger.info("[HandleBeforeDelete] : "+ e);
    if(AuditedAuditor.hasNotPermission("ROLE_ADMIN")){
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleBeforeRead
  public void handleBeforeRead(Account e, Specification<Account> s) throws Exception{
    logger.info("[HandleBeforeRead] : "+ e);

    if(AuditedAuditor.hasPermission("ROLE_ADMIN")){
      JpaSpecificationBuilder.of(Account.class).where().build(s);
    }else if(AuditedAuditor.hasPermission("ROLE_USER")){
      JpaSpecificationBuilder.of(Account.class).where().and().eq("id", AuditedAuditor.getCurrentUsername()).build(s);
    }else{
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleAfterRead
  public void handleAfterRead(Account e, Serializable s) throws Exception{
    logger.info("[HandleAfterRead] : "+ e);
    if(!(AuditedAuditor.hasPermission("ROLE_ADMIN") || AuditedAuditor.hasPermission("ROLE_USER"))) {
      throw ResponseStatusExceptions.UNAUTHORIZED;
    }
  }

  @HandleAfterSave
  public void handleAfterSave(Account e) throws Exception{
    logger.info("[HandleAfterSave] : "+ e);
  }

  @HandleAfterDelete
  public void handleAfterDelete(Account e) throws Exception{
    logger.info("[HandleAfterDelete] : "+ e);
  }
}
