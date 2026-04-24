package backend.stomp.sessions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import backend.domain.Session;
import backend.rest.sessions.SessionRepository;
import io.u2ware.common.stomp.client.WebsocketStompClient;
import io.u2ware.common.stomp.client.WebsocketStompClientHandler;
import io.u2ware.common.stomp.client.config.WebsocketStompProperties;

@Component
public class SessionSubscriber implements WebsocketStompClientHandler{

  protected Log logger = LogFactory.getLog(getClass());

  @Autowired
  protected SessionRepository nRepository;

  @Autowired
  protected ObjectMapper mapper;

  @Autowired
  protected WebsocketStompProperties properties;

  @Override
  public void handleFrame(WebsocketStompClient client, JsonNode message){
    logger.info("Received: " + message);

    Long timestamp = message.get("timestamp").asLong();
    String principal = message.get("principal").asText();
    String state = message.get("payload").get("state").asText();

    Session e = new Session();
    e.setPrincipal(principal);
    e.setState(state);
    e.setTimestamp(timestamp);
    nRepository.save(e);
  }
  
}
