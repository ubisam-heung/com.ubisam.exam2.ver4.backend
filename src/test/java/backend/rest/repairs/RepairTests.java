package backend.rest.repairs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static io.u2ware.common.docs.MockMvcRestDocs.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.JsonNode;

import backend.oauth2.Oauth2Docs;
import backend.rest.buses.BusDocs;
import io.u2ware.common.stomp.client.WebsocketStompClient;
import io.u2ware.common.stomp.client.config.WebsocketStompProperties;
import io.u2ware.common.stomp.client.handlers.StompJsonFrameHandler;

@SpringBootTest
@AutoConfigureMockMvc
public class RepairTests {

  protected Log logger = LogFactory.getLog(getClass());

  @Autowired
  private MockMvc mvc;

  @Autowired
  private Oauth2Docs od;

  @Autowired
  private BusDocs bd;

  @Autowired
  private WebsocketStompProperties properties;

  @Autowired
  private RepairDocs rd;

  @Autowired
  private WebsocketStompClient wsc;

  @Test
  public void contextLoads() throws Exception{
    // 사전 설정
    CompletableFuture<Void> sent = new CompletableFuture<>();
    CompletableFuture<Void> received = new CompletableFuture<>();

    Jwt u = od.jose("admin", "ROLE_ADMIN");

    Set<Object> busTypeValue = new HashSet<>();
    busTypeValue.add("간선");
    busTypeValue.add("지선");

    Map<String, Object> req = new HashMap<>();
    req.put("title", "entity1");
    req.put("busNumber", 11140);
    req.put("busType", busTypeValue);

    mvc.perform(post("/rest/buses").content(req).auth(u)).andExpect(is2xx()).andDo(result(bd::context, "entity1"));
    String uri = bd.context("entity1", "$._links.self.href");

    String url = properties.getUrl();

    boolean isReady = false;
    for(int i = 0; i < 50; i++){
      if (wsc.isConnected()){
        Thread.sleep(400);
        isReady = true;
        break;
      }
      Thread.sleep(100);
    }
    if(!isReady){
      logger.error("Stomp 연결 에러!");
    }

    WebsocketStompClient.withSockJS().connect(url).whenComplete((c, e) -> {
      if (e != null){
        sent.completeExceptionally(e);
        received.completeExceptionally(e);
        return;
      }

      c.subscribe("/topic/repairs", new StompJsonFrameHandler() {
        @Override
        public void handleFrame(StompHeaders headers, JsonNode payload){
          if (rd.isReceived(payload)){
            received.complete(null);
          }
        }
      }).whenComplete((c1, e1) -> {
        if (e1 != null){
          sent.completeExceptionally(e1);
          received.completeExceptionally(e1);
          return;
        }
        Map<String, Object> message = new HashMap<>();
        message.put("busNumber", 11140);
        message.put("contents", "11140 테스트 버스 수리완료");

        c.send("/app/repairs", message).whenComplete((r, e2) -> {
          if(e2 != null){
            sent.completeExceptionally(e2);
            received.completeExceptionally(e2);
            return;
          }
          sent.complete(null);
        });
      });
    });
    sent.get(10, TimeUnit.SECONDS);
    received.get(10, TimeUnit.SECONDS);

    // Read
    mvc.perform(post(uri).auth(u)).andDo(print()).andExpect(is2xx()).andDo(result(bd::context, "entity2"));
    String checkMessage = bd.context("entity2", "$.busRepairHistory[0].busRepairState");
    assertTrue(checkMessage.equals("11140 테스트 버스 수리완료"));
  }
}
