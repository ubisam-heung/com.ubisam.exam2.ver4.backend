package backend.rest.sessions;

import static io.u2ware.common.docs.MockMvcRestDocs.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import backend.oauth2.Oauth2Docs;

@SpringBootTest
@AutoConfigureMockMvc
public class SessionTests {

  protected Log logger = LogFactory.getLog(getClass());

  @Autowired
  private MockMvc mvc;

  @Autowired
  private Oauth2Docs od;

  @Test
  void contextLoads() throws Exception{

    // 유저 설정
    Jwt u1 = od.jose("admin", "ROLE_ADMIN");
    Jwt u2 = od.jose("user", "ROLE_USER");

    mvc.perform(post("/rest/sessions/search")).andExpect(is4xx());
    mvc.perform(post("/rest/sessions/search").auth(u1)).andExpect(is2xx());
    mvc.perform(post("/rest/sessions/search").auth(u2)).andExpect(is4xx());

  }
  
}
