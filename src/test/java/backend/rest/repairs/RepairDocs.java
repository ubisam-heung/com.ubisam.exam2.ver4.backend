package backend.rest.repairs;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import io.u2ware.common.docs.MockMvcRestDocs;

@Component
public class RepairDocs extends MockMvcRestDocs{

  public boolean isReceived(JsonNode payload){
    if(payload == null){
      return false;
    }
    JsonNode value = payload.path("Received Message");
    if(value.isMissingNode()){
      value = payload.path("payload").path("Received Message");
    }
    return !value.isMissingNode() && "수리를 완료했어요!".equals(value.asText(""));
  }
  
}
