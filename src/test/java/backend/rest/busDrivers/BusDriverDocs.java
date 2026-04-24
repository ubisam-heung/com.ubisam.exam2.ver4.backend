package backend.rest.busDrivers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import backend.domain.BusDriver;
import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class BusDriverDocs extends MockMvcRestDocs{

  public BusDriver newEntity(String ... entity){
    BusDriver body = new BusDriver();
    body.setBusDriverName(entity.length > 0 ? entity[0] : super.randomText("busDriverName"));
    body.setBusDriverLicense(entity.length > 1 ? entity[1] : super.randomText("busDriverLicense"));
    return body;
  }

  public Map<String, Object> updateEntity(Map<String, Object> body, String entity){
    body.put("busDriverName", entity);
    return body;
  }

  public Map<String, Object> setSearch(String keyword, String option){
    Map<String, Object> body = new HashMap<>();
    body.put("keyword", keyword);
    body.put("option", option);
    return body;
  }

  
}
