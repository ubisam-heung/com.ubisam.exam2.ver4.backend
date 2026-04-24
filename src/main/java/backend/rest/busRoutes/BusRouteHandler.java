package backend.rest.busRoutes;

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

import backend.domain.BusRoute;
import backend.domain.BusStop;
import backend.domain.properties.LinkConversion;
import io.u2ware.common.data.jpa.repository.query.JpaSpecificationBuilder;
import io.u2ware.common.data.rest.core.annotation.HandleAfterRead;
import io.u2ware.common.data.rest.core.annotation.HandleBeforeRead;

@Component
@RepositoryEventHandler
public class BusRouteHandler {

  protected Log logger = LogFactory.getLog(getClass());

  private @Autowired LinkConversion linkConversion;

	private void conversion(BusRoute e) throws Exception{
		logger.info("conversion1 "+ e.getBusStopLinks());

		linkConversion.convertWithEntity(BusStop.class, e.getBusStopLinks(), ref->{e.setBusStops(ref);});
	}

  @HandleBeforeRead
  public void beforeRead(BusRoute e, Specification<BusRoute> spec) throws Exception{
    logger.info("[HandleBeforeRead] : "+e);
    //쿼리 작성
    JpaSpecificationBuilder<BusRoute> query = JpaSpecificationBuilder.of(BusRoute.class);
    String keyword = e.getKeyword();
    String option = e.getOption();
    if(keyword == null || keyword.trim().isEmpty()){
      query.where().build(spec);
      return;
    }
    switch (option) {
      case "busAll":
        query.where()
          .and().like("busRouteName", "%"+keyword+"%")
          .or().like("busRouteStart", "%"+keyword+"%")
          .or().like("busRouteEnd", "%"+keyword+"%")
          .build(spec);
        break;
      case "busRouteName":
        query.where()
          .and().like("busRouteName", "%"+keyword+"%")
          .build(spec);
        break;
      case "busRouteStart":
        query.where()
          .and().like("busRouteStart", "%"+keyword+"%")
          .build(spec);
        break;
      case "busRouteEnd":
        query.where()
          .and().like("busRouteEnd", "%"+keyword+"%")
          .build(spec);
        break;
      default:
        query.where()
          .and().like("busRouteName", "%"+keyword+"%")
          .or().like("busRouteStart", "%"+keyword+"%")
          .or().like("busRouteEnd", "%"+keyword+"%")
          .build(spec);
    }
  }

  @HandleAfterRead
  public void afterRead(BusRoute e, Serializable r) throws Exception{
    logger.info("[HandleafterRead] "+e);
    logger.info("[HandleafterRead] "+r);
  }

  @HandleBeforeCreate
  public void beforeCreate(BusRoute e) throws Exception{
    conversion(e);
    logger.info("[HandlebeforeCreate] "+e);
  }

  @HandleBeforeSave
  public void beforeSave(BusRoute e) throws Exception{
    conversion(e);
    logger.info("[HandlebeforeSave] "+e);
  }
  
  @HandleBeforeDelete
  public void beforeDelete(BusRoute e) throws Exception{
    logger.info("[HandlebeforeDelete] "+e);
  }

  @HandleAfterCreate
  public void afterCreate(BusRoute e) throws Exception{
    logger.info("[HandleafterCreate] "+e);
  }

  @HandleAfterSave
  public void afterSave(BusRoute e) throws Exception{
    logger.info("[HandleafterSave] "+e);
  }
  
  @HandleAfterDelete
  public void afterDelete(BusRoute e) throws Exception{
    logger.info("[HandleafterDelete] "+e);
  }
  
}
