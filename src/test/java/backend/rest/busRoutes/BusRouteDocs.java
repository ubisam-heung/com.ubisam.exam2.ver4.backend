package backend.rest.busRoutes;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import backend.domain.BusRoute;
import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusRouteDocs extends MockMvcRestDocs{

  public BusRoute newEntity(String... entity){
    BusRoute body = new BusRoute();
    body.setBusRouteName(entity.length > 0 ? entity[0] : super.randomText("busRouteName"));
    body.setBusRouteStart(entity.length > 1 ? entity[1] : super.randomText("busRouteStart"));
    body.setBusRouteEnd(entity.length > 2 ? entity[2] : super.randomText("busRouteEnd"));
    return body;
  }
  
  public Map<String, Object> setSearch(String keyword, String option){
    Map<String, Object> body = new HashMap<>();
    body.put("keyword", keyword);
    body.put("option", option);
    return body;
  }
}
