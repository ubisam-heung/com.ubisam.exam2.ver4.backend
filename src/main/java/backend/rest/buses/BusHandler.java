package backend.rest.buses;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import backend.domain.Bus;
import backend.domain.BusDriver;
import backend.domain.BusRoute;
import backend.domain.properties.LinkConversion;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BusHandler {

  protected Log logger = LogFactory.getLog(getClass());

  @Autowired
  private LinkConversion linkConversion;

  public void conversion(Bus e) throws Exception{
		logger.info("conversion1 "+e.getBusRouteLink());
		logger.info("conversion1 "+e.getBusDriverLink());

		linkConversion.convertWithEntity(BusDriver.class, e.getBusDriverLink(), ref->{e.setBusDriver(ref);});
    linkConversion.convertWithEntity(BusRoute.class, e.getBusRouteLink(), ref->{e.setBusRoute(ref);});
  }

  @HandleBeforeRead
  public void beforeRead(Bus e, Specification<Bus> spec) throws Exception{
    logger.info("[HandleBeforeRead] : "+e);
    //쿼리 작성
    JpaSpecificationBuilder<Bus> query = JpaSpecificationBuilder.of(Bus.class);
    String keyword = e.getKeyword();
    String option = e.getOption();
    if(keyword == null || keyword.trim().isEmpty()){
      query.where().build(spec);
      return;
    }
    switch (option) {
      case "busAll":
        try {
          int n = Integer.parseInt(keyword);
          query
            .where().and().eq("busNumber", n)
            .or().like("busType", "%" + keyword + "%")
            .build(spec);
        } catch (NumberFormatException ex) {
          query
            .where().and().like("busType", "%" + keyword + "%")
            .build(spec);
        }
        break;
      case "busNumber":
        try {
          int n = Integer.parseInt(keyword);
          query
            .where().and().eq("busNumber", n)
            .build(spec);
        } catch (NumberFormatException ex) {
          query
            .where().and().eq("busNumber", -1)
            .build(spec);
        }
        break;
      case "busType":
        query
          .where().and().like("busType", "%" + keyword + "%")
          .build(spec);
        break;
      default:
        query.where().build(spec);
    }
  }

  @HandleAfterRead
  public void afterRead(Bus e, Serializable r) throws Exception{
    logger.info("[HandleafterRead] "+e);
    logger.info("[HandleafterRead] "+r);
  }

  @HandleBeforeCreate
  public void beforeCreate(Bus e) throws Exception{
    conversion(e);
    logger.info("[HandlebeforeCreate] "+e);
  }

  @HandleBeforeSave
  public void beforeSave(Bus e) throws Exception{
    conversion(e);
    logger.info("[HandlebeforeSave] "+e);
  }
  
  @HandleBeforeDelete
  public void beforeDelete(Bus e) throws Exception{
    logger.info("[HandlebeforeDelete] "+e);
  }

  @HandleAfterCreate
  public void afterCreate(Bus e) throws Exception{
    logger.info("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(Bus e) throws Exception{
    logger.info("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(Bus e) throws Exception{
    logger.info("[HandleafterDelete] "+e);
  }
  
}
