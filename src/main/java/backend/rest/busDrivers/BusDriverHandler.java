package backend.rest.busDrivers;

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

import backend.domain.BusDriver;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BusDriverHandler {

  protected Log logger = LogFactory.getLog(getClass());

  @HandleBeforeRead
  public void beforeRead(BusDriver e, Specification<BusDriver> spec) throws Exception{
    logger.info("[HandleBeforeRead] : "+e);
    //쿼리 작성
    JpaSpecificationBuilder<BusDriver> query = JpaSpecificationBuilder.of(BusDriver.class);
    String keyword = e.getKeyword();
    String option = e.getOption();
    if(keyword == null || keyword.trim().isEmpty()){
      query.where().build(spec);
      return;
    }
    switch (option) {
      case "busAll":
        query.where()
          .and().like("busDriverName", "%"+keyword+"%")
          .or().like("busDriverLicense", "%"+keyword+"%")
          .build(spec);
        break;
      case "busDriverName":
        query.where()
          .and().like("busDriverName", "%"+keyword+"%")
          .build(spec);
        break;
      case "busDriverLicense":
        query.where()
          .and().like("busDriverLicense", "%"+keyword+"%")
          .build(spec);
        break;
      default:
        query.where()
          .and().like("busDriverName", "%"+keyword+"%")
          .or().like("busDriverLicense", "%"+keyword+"%")
          .build(spec);
    }
  }

  @HandleAfterRead
  public void afterRead(BusDriver e, Serializable r) throws Exception{
    logger.info("[HandleafterRead] "+e);
    logger.info("[HandleafterRead] "+r);
  }

  @HandleBeforeCreate
  public void beforeCreate(BusDriver e) throws Exception{
    logger.info("[HandlebeforeCreate] "+e);
  }

  @HandleBeforeSave
  public void beforeSave(BusDriver e) throws Exception{
    logger.info("[HandlebeforeSave] "+e);
  }
  
  @HandleBeforeDelete
  public void beforeDelete(BusDriver e) throws Exception{
    logger.info("[HandlebeforeDelete] "+e);
  }

  @HandleAfterCreate
  public void afterCreate(BusDriver e) throws Exception{
    logger.info("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(BusDriver e) throws Exception{
    logger.info("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(BusDriver e) throws Exception{
    logger.info("[HandleafterDelete] "+e);
  }
  
}
