package backend.rest.busStops;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import backend.domain.BusStop;
import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusStopDocs extends MockMvcRestDocs{

  public BusStop newEntity(String... entity){
    BusStop body = new BusStop();
    body.setBusStopName(entity.length > 0 ? entity[0] : super.randomText("busStopName"));
    body.setBusStopLocation(entity.length > 1 ? entity[1] : super.randomText("busStopLocation"));
    return body;
  }

  public Map<String, Object> updateEntity(Map<String, Object> body, String entity){
    body.put("busStopName", entity);
    return body;
  }
  
  public Map<String, Object> setSearch(String keyword, String option){
    Map<String, Object> body = new HashMap<>();
    body.put("keyword", keyword);
    body.put("option", option);
    return body;
  }
}
