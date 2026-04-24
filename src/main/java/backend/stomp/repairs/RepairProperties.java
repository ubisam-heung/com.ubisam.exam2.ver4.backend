package backend.stomp.repairs;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class RepairProperties {
  protected String[] fieldKeys = {"busType", "busNumber"};
  protected String payloadText = "수리";
  protected String destination = "repairs";
  protected String messageKey = "contents";
  protected String receivedMessageKey = "Received Message";
  protected String completedMessage = "수리를 완료했어요!";
  
}
